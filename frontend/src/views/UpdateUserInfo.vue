<template>
  <v-container fill-height style="max-width:450px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-card>
          <v-toolbar flat dense>
            <v-toolbar-title>
              <h2>회원정보수정</h2>
            </v-toolbar-title>
          </v-toolbar>
          <div class="pa-3">
            <div>
              아이디 : {{userInfo.uid}}
              <v-text-field label="비밀번호" type="password" v-model="password"></v-text-field>
              <v-text-field label="이름" v-model="name"></v-text-field>
              <v-btn @click="updateUsrInfo({password,name})">수정하기</v-btn>
            </div>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  computed: {
    ...mapState(["userInfo"])
  },
  data() {
    return {
      uid: null,
      name: null
    };
  },
  created() {
    (this.uid = this.userInfo.uid),
      (this.name = this.userInfo.name),
      console.log(this.userInfo.uid, this.userInfo.name);
  },
  methods: {
    ...mapActions(["updateUsrInfo"])
  }
};
</script>
