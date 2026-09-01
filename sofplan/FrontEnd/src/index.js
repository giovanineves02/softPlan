import { createRouter, createWebHistory } from 'vue-router'

// Páginas Públicas / Isoladas (SEM layout)
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'

// Layout do Painel e Páginas Internas
import AppLayout from '../layouts/AppLayout.vue'
import UploadView from '../views/Upload.vue'
import RelatoriosView from '../views/Relatorios.vue'
import GraficosView from '../views/Graficos.vue'

const routes = [
  // 1. Tela Home totalmente isolada (não é filha do AppLayout)
  {
    path: '/',
    name: 'Home',
    component: Home
  },

  // 2. Tela de Login também isolada
  {
    path: '/login',
    name: 'Login',
    component: Login
  },

  // 3. Estrutura com Sidebar / AppLayout no caminho /sidebar
  {
    path: '/sidebar',
    component: AppLayout,
    redirect: '/sidebar/upload', // Redireciona automaticamente ao acessar /sidebar
    children: [
      {
        path: 'upload',
        name: 'Upload',
        component: UploadView
      },
      {
        path: 'relatorios',
        name: 'Relatorios',
        component: RelatoriosView
      },
      {
        path: 'graficos',
        name: 'Graficos',
        component: GraficosView
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router