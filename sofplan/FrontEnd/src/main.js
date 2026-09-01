import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // Importa as rotas de src/router/index.js
import './style.css' // Importa o CSS global / Tailwind CSS

const app = createApp(App)

app.use(router)
app.mount('#app')