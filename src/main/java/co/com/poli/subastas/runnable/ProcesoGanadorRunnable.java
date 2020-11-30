/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.poli.subastas.runnable;

import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.domain.Subastas;
import co.com.poli.subastas.domain.User;
import co.com.poli.subastas.domain.enumeration.EstadoPujadores;
import co.com.poli.subastas.repository.EventosRepository;
import co.com.poli.subastas.repository.LotesRepository;
import co.com.poli.subastas.repository.PujadoresRepository;
import co.com.poli.subastas.repository.PujasRepository;
import co.com.poli.subastas.repository.SubastasRepository;
import co.com.poli.subastas.repository.UserRepository;
import co.com.poli.subastas.service.MailService;
import io.github.jhipster.security.RandomUtil;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 57304
 */
public class ProcesoGanadorRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ProcesoGanadorRunnable.class);
    private final EventosRepository eventosRepository;
    private final SubastasRepository subastasRepository;
    private final PujasRepository pujasRepository;
    private final PujadoresRepository pujadoresRepository;
    private final LotesRepository lotesRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private List<Pujas> listPujas;
    private Subastas subasta;

    public ProcesoGanadorRunnable(EventosRepository eventosRepository, SubastasRepository subastasRepository, PujasRepository pujasRepository, PujadoresRepository pujadoresRepository, LotesRepository lotesRepository, UserRepository userRepository, MailService mailService, List<Pujas> listPujas, Subastas subasta) {
        this.eventosRepository = eventosRepository;
        this.subastasRepository = subastasRepository;
        this.pujasRepository = pujasRepository;
        this.pujadoresRepository = pujadoresRepository;
        this.lotesRepository = lotesRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.subasta = subasta;
        this.listPujas = listPujas;
    }

    @Override
    public void run() {
        synchronized (listPujas) {
            main:
            for (int i = 0; i < listPujas.size(); i++) {
                Pujas puja = listPujas.get(i);
                Calendar alarmCalendar = Calendar.getInstance();
                alarmCalendar.set(Calendar.MINUTE, this.subasta.getTimpoRecloGanador());
                long alarmTime = alarmCalendar.getTimeInMillis();
                if (determinarGanador(i, puja)) break main;
                try {
                    listPujas.wait(alarmTime);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(ProcesoGanadorRunnable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean determinarGanador(int i, Pujas puja) throws NumberFormatException {
        if (i == 0) {
            enviarMensaje(puja);
        } else {
            Pujas pujaOld = listPujas.get(i-1);
            Pujadores pujadores = this.pujadoresRepository.findById(puja.getPujadores().getId()).get();
            if (pujadores.getEstado().equals(EstadoPujadores.GANADOR)) {
                return true;
            } else {
                enviarMensaje(puja);
            }
        }
        return false;
    }

    private void enviarMensaje(Pujas puja) throws NumberFormatException {
        Pujadores pujador = puja.getPujadores();
        pujador.setActivationKey(RandomUtil.generateActivationKey());
        User user = this.userRepository.findOneByLogin(pujador.getCliente().getIdusuario()).get();
        mailService.sendAceptarGanadorEmail(user, pujador);
        this.pujadoresRepository.save(pujador);
    }

}
