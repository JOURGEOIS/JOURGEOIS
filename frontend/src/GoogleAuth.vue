<template>
  <button @click="signIn">login</button>
  <button @click="signOut">log out</button>
  <div>{{ user }}</div>
</template>
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { installGoogleAuth } from './GoogleAuth';
export default defineComponent({
  name: 'SignIn',
  setup(props, { emit }) {
    let gAuth: any;
    const user = ref({});
    const options = {
      clientId: `${process.env.GOOGLE_CLIENT_ID}.apps.googleusercontent.com`,
      scope: 'profile email',
      prompt: 'select_account'
    };
    function signIn(): void {
      if (!gAuth) return;
      gAuth
        .signIn()
        .then((googleUser: any) => {
          user.value = googleUser;
        })
        .catch((e: any) => {
          console.log('error', e);
        });
    }
    
    function signOut(): void {
      if (!gAuth) return;
      gAuth.signOut();
    }
    onMounted(async () => {
      gAuth = installGoogleAuth(options);
    });
    return { user, signIn, signOut };
  }
});
</script>