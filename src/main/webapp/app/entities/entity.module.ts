import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dispositivo',
        loadChildren: () => import('./dispositivo/dispositivo.module').then(m => m.SubastasDispositivoModule)
      },
      {
        path: 'estado-cliente',
        loadChildren: () => import('./estado-cliente/estado-cliente.module').then(m => m.SubastasEstadoClienteModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.SubastasClienteModule)
      },
      {
        path: 'pujadores',
        loadChildren: () => import('./pujadores/pujadores.module').then(m => m.SubastasPujadoresModule)
      },
      {
        path: 'pujas',
        loadChildren: () => import('./pujas/pujas.module').then(m => m.SubastasPujasModule)
      },
      {
        path: 'subastas',
        loadChildren: () => import('./subastas/subastas.module').then(m => m.SubastasSubastasModule)
      },
      {
        path: 'eventos',
        loadChildren: () => import('./eventos/eventos.module').then(m => m.SubastasEventosModule)
      },
      {
        path: 'clasificacion-lote',
        loadChildren: () => import('./clasificacion-lote/clasificacion-lote.module').then(m => m.SubastasClasificacionLoteModule)
      },
      {
        path: 'mensajes',
        loadChildren: () => import('./mensajes/mensajes.module').then(m => m.SubastasMensajesModule)
      },
      {
        path: 'especies',
        loadChildren: () => import('./especies/especies.module').then(m => m.SubastasEspeciesModule)
      },
      {
        path: 'razas',
        loadChildren: () => import('./razas/razas.module').then(m => m.SubastasRazasModule)
      },
      {
        path: 'departamentos',
        loadChildren: () => import('./departamentos/departamentos.module').then(m => m.SubastasDepartamentosModule)
      },
      {
        path: 'municipios',
        loadChildren: () => import('./municipios/municipios.module').then(m => m.SubastasMunicipiosModule)
      },
      {
        path: 'ciudad',
        loadChildren: () => import('./ciudad/ciudad.module').then(m => m.SubastasCiudadModule)
      },
      {
        path: 'contenido',
        loadChildren: () => import('./contenido/contenido.module').then(m => m.SubastasContenidoModule)
      },
      {
        path: 'tipo-documento',
        loadChildren: () => import('./tipo-documento/tipo-documento.module').then(m => m.SubastasTipoDocumentoModule)
      },
      {
        path: 'propietario',
        loadChildren: () => import('./propietario/propietario.module').then(m => m.SubastasPropietarioModule)
      },
      {
        path: 'lotes',
        loadChildren: () => import('./lotes/lotes.module').then(m => m.SubastasLotesModule)
      },
      {
        path: 'animales',
        loadChildren: () => import('./animales/animales.module').then(m => m.SubastasAnimalesModule)
      },
      {
        path: 'lotes-to-animales',
        loadChildren: () => import('./lotes-to-animales/lotes-to-animales.module').then(m => m.SubastasLotesToAnimalesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SubastasEntityModule {}
