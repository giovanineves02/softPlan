<template>
  <header class="bg-transparent px-4 py-4 text-white md:px-12">
    <div class="mx-auto flex max-w-7xl flex-col items-center justify-between gap-4 md:flex-row">
      <div class="flex items-center gap-3">
        <img :src="logo" alt="Logo" class="h-10 w-10 rounded-md object-cover" />

        <div class="flex items-center">
          <h1 class="font-bold">SOFT</h1>
          <h1 class="font-bold text-[#22D3EE]">PLAN</h1>
        </div>
      </div>

      <nav class="w-full md:w-auto">
        <ul class="flex w-full items-center justify-center gap-4 text-base font-semibold md:w-auto md:text-lg">
          <li>
            <button type="button"
              class="cursor-pointer px-1 py-2 font-semibold text-white animate-pulse transition-colors duration-200 hover:text-[#22D3EE]">
              Inicio
            </button>
          </li>
          <li>
            <button type="button"
              class="cursor-pointer px-1 py-2 font-semibold text-white animate-pulse transition-colors duration-200 hover:text-[#22D3EE]">
              Recursos
            </button>
          </li>
          <li>
            <button type="button"
              class="cursor-pointer px-1 py-2 font-semibold text-white animate-pulse transition-colors duration-200 hover:text-[#22D3EE]">
              Sobre
            </button>
          </li>
        </ul>
      </nav>

      <button
        type="button"
        @click="irParaLogin"
        class="w-full cursor-pointer rounded-lg bg-[#2563EB] px-4 py-2 font-semibold text-white transition-all duration-200 hover:-translate-y-0.5 hover:bg-[#22D3EE] hover:text-[#030D24] hover:shadow-[0_0_24px_rgba(34,211,238,0.7)] active:translate-y-0 md:w-auto">
        Entrar
      </button>
    </div>
  </header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import logo from '../assets/logo.png'

const router = useRouter()

function irParaLogin() {
  router.push('/login')
}
</script>