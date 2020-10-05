<template>
  <button>확인</button>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
export default {
  methods: {
    ...mapActions(["kakaosignin"])
  },
  mounted() {
    console.log(this.$route.query.code);
    const qs = require("qs");
    let accessToken = null;
    const parameter = {
      grant_type: "authorization_code",
      client_id: "9711a4064e369abb0bab51007aa18a4b",
      redirect_uri: `${window.location.origin}/sociallogin`,
      code: this.$route.query.code
    };
    axios
      .post("https://kauth.kakao.com/oauth/token", qs.stringify(parameter), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
        }
      })
      .then(res => {
        console.log(res);
        accessToken = res.data.access_token;
        console.log(accessToken);
        this.kakaosignin(accessToken);
        // this.$router.push({
        //   name: "signin",
        //   params: {
        //     accessToken
        //   }
        // });
      });
  }
};
</script>

// Request:
// curl -v -X POST https://kauth.kakao.com/oauth/token \
//  -d 'grant_type=authorization_code' \
//  -d 'client_id={REST_API_KEY}' \
//  -d 'redirect_uri={REDIRECT_URI}' \
//  -d 'code={AUTHORIZE_CODE}'

// Response:Success
//  HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
// {
//     "token_type":"bearer",
//     "access_token":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
//     "expires_in":43199,
//     "refresh_token":"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
//     "refresh_token_expires_in":25184000,
//     "scope":"account_email profile"