/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.poli.subastas.scheduling;

import co.com.poli.subastas.repository.EventosRepository;
import co.com.poli.subastas.repository.SubastasRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import co.com.poli.subastas.domain.Eventos;
import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.domain.Subastas;
import co.com.poli.subastas.domain.User;
import co.com.poli.subastas.domain.enumeration.EstadoPujadores;
import co.com.poli.subastas.repository.LotesRepository;
import co.com.poli.subastas.repository.PujadoresRepository;
import co.com.poli.subastas.repository.PujasRepository;
import co.com.poli.subastas.repository.UserRepository;
import co.com.poli.subastas.runnable.ProcesoGanadorRunnable;
import co.com.poli.subastas.service.MailService;
import io.github.jhipster.security.RandomUtil;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author 57304
 */
@Component
public class ProcesoGanadorScheduling {

    private static final Logger log = LoggerFactory.getLogger(ProcesoGanadorScheduling.class);
    private final EventosRepository eventosRepository;
    private final SubastasRepository subastasRepository;
    private final PujasRepository pujasRepository;
    private final PujadoresRepository pujadoresRepository;
    private final LotesRepository lotesRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    public ProcesoGanadorScheduling(EventosRepository eventosRepository, SubastasRepository subastasRepository, PujasRepository pujasRepository, PujadoresRepository pujadoresRepository, LotesRepository lotesRepository, UserRepository userRepository,MailService mailService) {
        this.eventosRepository = eventosRepository;
        this.subastasRepository = subastasRepository;
        this.pujasRepository = pujasRepository;
        this.pujadoresRepository = pujadoresRepository;
        this.lotesRepository = lotesRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Scheduled(cron = "* 5 * * * *", zone = "America/Bogota")
    public void desactivarEventos() {
        log.info("-----------------------------desactivarEventos init---- ");
        List<Eventos> listEventos = this.eventosRepository.findAllWithFechafinalBefore(Instant.now(), Boolean.TRUE);
        for (Eventos evento : listEventos) {
            evento.setEstadoActivo(Boolean.FALSE);
        }
        this.eventosRepository.saveAll(listEventos);
        log.info("-----------------------------desactivarEventos end---- ");
    }

    @Scheduled(cron = "* 5 * * * *", zone = "America/Bogota")
    public void desactivarSubastas() {
        log.info("-----------------------------desactivarSubastas init---- ");
        List<Subastas> listSubastas = this.subastasRepository.findAllWithFechafinalBefore(Instant.now(), Boolean.TRUE, Boolean.FALSE);
        for (Subastas subasta : listSubastas) {
            subasta.setEstadoActivo(Boolean.FALSE);
        }
        this.subastasRepository.saveAll(listSubastas);
        log.info("-----------------------------desactivarSubastas end---- ");
    }

    @Scheduled(cron = "* 5 * * * *", zone = "America/Bogota")
    public void subastasProcesoGanador() {
        log.info("-----------------------------subastasProcesoGanador init---- ");
        List<Subastas> listSubastas = this.subastasRepository.findAllWithFechafinalBefore(Instant.now(),  Boolean.FALSE, Boolean.FALSE);
        for (Subastas subasta : listSubastas) {
            log.info(" subastasProcesoGanador subastas---- " + subasta.getId());
            List<Lotes> listLotes = this.lotesRepository.findBySubastas(subasta);
            for (Lotes lote : listLotes) {
                Long idSubastas = subasta.getId();
                Long idEvento = subasta.getEventos().getId();
                Long idLote = lote.getId();
                if (isNoGandaor(idEvento, idSubastas, idLote)) {
                    log.info(" idEvento " + idEvento.toString() + "  idSubastas " + idSubastas.toString() + " idLote " + idLote.toString());
                    Pujas pjMayor = pujaMayor(idEvento, idSubastas, idLote);
                    if (pjMayor != null) {
                        setValorTope100(subasta);
                        BigDecimal tope = (subasta.getValortope().divide(new BigDecimal(100))).multiply(pjMayor.getValor());
                        List<Pujas> listPujas = this.pujasRepository.findByIdEventoAndIdSubastaAndIdLoteAndValorGreaterThanOrderByValorDesc(idEvento.toString(), idSubastas.toString(), idLote.toString(), tope);
                        if (!listPujas.isEmpty()) {
                            subasta.setEstadoGanador(Boolean.TRUE);
                            
                            
                            Runnable task = new ProcesoGanadorRunnable(eventosRepository, subastasRepository, pujasRepository, pujadoresRepository, lotesRepository, userRepository,mailService, listPujas,subasta);
                            new Thread(task, "Notificando: ").start();
                            this.subastasRepository.save(subasta);
                        }
                    }
                }
            }
        }
        log.info("-----------------------------subastasProcesoGanador end---- ");
    }

    private void setValorTope100(Subastas subasta) {
        if (subasta.getValortope() == null) {
            subasta.setValortope(new BigDecimal(100));
        }
    }

    private Pujas pujaMayor(Long idEvento, Long idSubastas, Long idLote) {
        List<Pujas>  listPujas = this.pujasRepository.findByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(idEvento.toString(), idSubastas.toString(), idLote.toString());
        System.out.println(listPujas);
        if(!listPujas.isEmpty()){
            return listPujas.get(0);
        }
        return null;
    } 

    private Boolean isNoGandaor(Long idEvento, Long idSubastas, Long idLote) {
        Boolean isNoGanador = this.pujadoresRepository.findByIdEventoAndIdSubastaAndIdLoteAndEstado(idEvento.toString(), idSubastas.toString(), idLote.toString(), EstadoPujadores.GANADOR).isEmpty();
        return isNoGanador;
    }

}
