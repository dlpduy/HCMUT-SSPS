import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  base: './', // Quan trọng để Vercel load asset đúng
  plugins: [react()],
})
