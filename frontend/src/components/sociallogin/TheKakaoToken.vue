<template>
  <div>
    로그인 진행중
  </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'
const route = useRoute()
const router = useRouter()

const get_token = () => {
  const code = route.params.code
  axios({
    method: 'POST',
    url: 'https://kauth.kakao.com/oauth/token',
    params: {
      grant_type: 'authorization_code',
      client_id: '발급키',
      redirect_uri: 'http://localhost:8080/Loginservice/code',
      code,
    },
  })
    .then((Response) => {
      console.log('response : ', Response)
      const token = Response.data.access_token
      console.log('access_token : ', token)
      sessionStorage.setItem('usertoken', token)
      router.replace('/main')
    })
    return {
      code: '',
      token: '',
      au: '',
    }
}
</script>

<style scoped>

</style>


</script>
<style></style>
