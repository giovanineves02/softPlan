<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import logo from "../assets/logo.png";

const router = useRouter();

const email = ref("");
const password = ref("");
const erro = ref("");
const carregando = ref(false);

async function handleSubmit() {
  erro.value = "";
  carregando.value = true;

  try {
    const apiUrl = import.meta.env.VITE_API_URL ?? "";
    const response = await fetch(`${apiUrl}/api/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ email: email.value, senha: password.value }),
    });

    if (!response.ok) {
      erro.value = response.status === 401
        ? "Usuário ou senha inválidos."
        : "Não foi possível conectar ao servidor. Tente novamente.";
      return;
    }

    router.push("/upload");
  } catch {
    erro.value = "Não foi possível acessar a API. Confirme que o Back-End está iniciado na porta 8080. As credenciais do DBeaver não são usadas neste formulário.";
  } finally {
    carregando.value = false;
  }
}

function voltarParaLanding() {
  router.push("/");
}
</script>

<template>
  <div class="bg-[#030d24] min-h-screen w-full relative overflow-hidden flex flex-col">
    <!-- Glow orbs -->
    <div class="absolute bg-[#2563eb] blur-[90px] left-[-112px] opacity-35 rounded-full size-[600px] top-[-70px] pointer-events-none" />
    <div class="absolute bg-[#0a1f5c] blur-[90px] left-[500px] opacity-35 rounded-full size-[500px] top-[288px] pointer-events-none" />
    <div class="absolute bg-[#22d3ee] blur-[90px] left-[413px] opacity-10 rounded-full size-[300px] top-[4px] pointer-events-none" />

    <!-- Navbar -->
    <div class="relative z-10 flex h-[64px] items-center justify-between max-w-[1152px] w-full mx-auto px-[24px]">
      <!-- Logo -->
      <div class="flex gap-[12px] items-center">
        <img :src="logo" alt="Logo SOFTPLAN" class="h-10 w-10 rounded-md object-cover" />
        <p class="font-bold text-white whitespace-nowrap">
          <span>SOFT</span><span class="text-[#22d3ee]">PLAN</span>
        </p>
      </div>

      <!-- Back button -->
      <button
        @click="voltarParaLanding"
        class="cursor-pointer bg-transparent border-none font-semibold text-[#93b8ff] hover:text-white transition-colors"
      >
        ← Voltar ao início
      </button>
    </div>

    <!-- Login card -->
    <div class="relative z-10 flex flex-1 items-center justify-center px-6 py-16">
      <form
        @submit.prevent="handleSubmit"
        class="bg-[rgba(7,25,61,0.9)] border border-[rgba(37,99,235,0.3)] rounded-[20px] p-[48px] w-full max-w-[576px] flex flex-col items-start"
      >
        <!-- Logo -->
        <div class="flex items-start justify-center w-full">
          <img :src="logo" alt="Logo SOFTPLAN" class="h-14 w-14 rounded-md object-cover" />
        </div>

        <!-- Heading -->
        <div class="flex flex-col h-[66px] items-center pt-[24px] w-full">
          <p class="font-bold leading-[42px] text-[#e8f0fe] text-[28px] text-center whitespace-nowrap w-full">
            Acesse o SOFTPLAN
          </p>
        </div>

        <!-- Subtitle -->
        <div class="flex flex-col items-center pt-[8px] w-full">
          <p
            class="font-normal leading-[23.04px] text-[#5b80b8] text-sm text-center w-full"
          >
            Entre com suas credenciais para acessar o painel de gestão de planilhas e dashboards.
          </p>
        </div>

        <!-- Inputs -->
        <div class="flex flex-col gap-[14px] pt-[32px] pb-[24px] w-full">
          <input
            type="email"
            v-model="email"
            placeholder="seu@email.com"
            autocomplete="username"
            class="bg-[rgba(2,8,24,0.7)] border border-[rgba(37,99,235,0.3)] rounded-[10px] h-[51.609px] px-[18px] w-full font-normal text-sm text-[#e8f0fe] placeholder:text-[rgba(232,240,254,0.5)] outline-none focus:border-[rgba(37,99,235,0.7)] transition-colors"
          />
          <input
            type="password"
            v-model="password"
            placeholder="Senha"
            autocomplete="current-password"
            class="bg-[rgba(2,8,24,0.7)] border border-[rgba(37,99,235,0.3)] rounded-[10px] h-[51.609px] px-[18px] w-full font-normal text-sm text-[#e8f0fe] placeholder:text-[rgba(232,240,254,0.5)] outline-none focus:border-[rgba(37,99,235,0.7)] transition-colors"
          />
        </div>

        <p v-if="erro" role="alert" class="mb-4 w-full text-sm text-rose-300">{{ erro }}</p>

        <!-- Submit button -->
        <button
          type="submit"
          :disabled="carregando"
          class="h-[52.781px] w-full cursor-pointer rounded-[10px] bg-[#2563EB] text-center text-base font-semibold text-white transition-all duration-200 hover:-translate-y-0.5 hover:bg-[#22D3EE] hover:text-[#030D24] hover:shadow-[0_0_24px_rgba(34,211,238,0.7)] active:translate-y-0"
        >
          {{ carregando ? "Verificando..." : "Entrar no sistema" }}
        </button>

        <!-- Forgot password -->
        <div class="flex flex-col h-[40px] items-center pt-[20px] w-full">
          <p
            class="font-normal text-[#5b80b8] text-xs text-center whitespace-nowrap"
          >
            Esqueceu a senha?
            <button
              type="button"
              class="text-[#22d3ee] bg-transparent border-none cursor-pointer hover:underline text-xs"
            >
              Recuperar acesso
            </button>
          </p>
        </div>
      </form>
    </div>

    <!-- Footer -->
    <div class="relative z-10 border-t border-[rgba(37,99,235,0.15)] flex flex-col items-center px-[24px] py-[32px] w-full">
      <div class="flex gap-[10px] items-center justify-center w-full">
        <img :src="logo" alt="Logo SOFTPLAN" class="h-6 w-6 rounded-md object-cover" />
        <p class="font-bold text-center text-white whitespace-nowrap">
          <span>SOFT</span><span class="text-[#22d3ee]">PLAN</span>
        </p>
      </div>
      <p
        class="font-normal leading-[18.72px] text-xs text-[#5b80b8] text-center whitespace-nowrap pt-[12px]"
      >
        Projeto Integrador · SENAI Robert Mange · 2026
      </p>
    </div>
  </div>
</template>