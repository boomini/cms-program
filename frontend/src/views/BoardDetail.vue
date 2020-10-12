<template>
  <v-container fill-height style="max-width:900px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-toolbar flat dense>
          <v-toolbar-title>글상세</v-toolbar-title>
        </v-toolbar>
        <div class="pa-3">
          <v-text label="제목"></v-text>
          <v-text
            solo
            rows="10"
            row-height="500"
            name="input-20-4"
            label="내용"
            v-model="content"
          ></v-text>
          <v-row align="center" justify="space-around">
            <v-btn
              router-link
              :to="{ name: 'boardlist' }"
              depressed
              color="primary"
              >목록</v-btn
            >
          </v-row>
          <div class="my-2"></div>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      uid: null,
      password: null,
      accessToken: null
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  mounted() {},
  methods: {}
};
</script>
