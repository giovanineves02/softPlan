<template>
  <div
    class="brand-mark"
    :style="{ '--brand-mark-size': `${size}px` }"
    aria-hidden="true"
  >
    <span class="brand-mark__grid"></span>
    <span class="brand-mark__bar brand-mark__bar--one"></span>
    <span class="brand-mark__bar brand-mark__bar--two"></span>
    <span class="brand-mark__bar brand-mark__bar--three"></span>
  </div>
</template>

<script setup lang="ts">
withDefaults(defineProps<{ size?: number }>(), {
  size: 56,
})
</script>

<style scoped>
.brand-mark {
  position: relative;
  width: var(--brand-mark-size);
  height: var(--brand-mark-size);
  flex-shrink: 0;
  overflow: hidden;
  border: calc(var(--brand-mark-size) * 0.0375) solid #2563eb;
  border-radius: calc(var(--brand-mark-size) * 0.12);
  background: #0a1f5c;
  box-shadow: 0 0 18px rgb(34 211 238 / 0.15);
}

.brand-mark__grid {
  position: absolute;
  inset: 25%;
  background:
    linear-gradient(90deg, transparent 42%, #1a44c2 42% 48%, transparent 48% 84%, #1a44c2 84% 90%, transparent 90%),
    linear-gradient(transparent 42%, #1a44c2 42% 48%, transparent 48% 84%, #1a44c2 84% 90%, transparent 90%);
}

.brand-mark__bar {
  position: absolute;
  bottom: 24%;
  width: 12%;
  border-radius: 999px 999px 0 0;
  background: #22d3ee;
}

.brand-mark__bar--one {
  left: 25%;
  height: 25%;
}

.brand-mark__bar--two {
  left: 44%;
  height: 42%;
  background: #4d84f5;
}

.brand-mark__bar--three {
  left: 63%;
  height: 62%;
}
</style>