import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Landing.vue'
import Login from '../views/Login.vue'
import Upload from '../views/upload.vue'
import Relatorio from '../views/Relatorio.vue'
import Dashboard from '../views/Dashboard.vue'
const routes = [
 {
   path: '/',
   name: 'Home',
  component: Home
 },
 {
   path: '/login',
   name: 'Login',
   component: Login
 },
 {
   path: '/upload',
   name: 'Upload',
   component: Upload
 },
 {
   path: '/relatorio',
   name: 'Relatorio',
   component: Relatorio
 },
 {
   path: '/dashboard',
   name: 'Dashboard',
   component: Dashboard
 }
]
const router = createRouter({
 history: createWebHistory(),
 routes
})
export default router