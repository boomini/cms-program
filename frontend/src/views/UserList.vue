<template>
  <v-container fill-height style="max-width:450px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-card class="mx-auto" tile>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>
                <h3>사용자리스트</h3>
              </v-list-item-title>
              <v-list-item-subtitle>사용자 수 : {{userList.length}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-list-item v-for="(list,id) in userList" :key="id">
            <v-list-item-content>
              <v-list-item-title>아이디 : {{list.uid}}</v-list-item-title>
              <v-list-item-subtitle>이름 :{{list.name}}</v-list-item-subtitle>
              <v-list-item-subtitle v-if="list.authority==='ROLE_USER'">역할 :사용자</v-list-item-subtitle>
              <v-list-item-subtitle v-else>역할 :관리자</v-list-item-subtitle>
              <div class="my-2" align="right">
                <v-btn depressed small color="error" @click="userDelete(list.uid)">회원삭제</v-btn>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>


    
<script>
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      uid: null,
      password: null,
      role: null
    };
  },
  computed: {
    ...mapState(["userList"])
  },
  methods: {
    ...mapActions(["getUserList", "userDelete"])
  },
  created: {
    checkRole() {
      if (this.userList.authority == "ROLE_USER") {
        console.log(this.userList.authority);
        this.role = "사용자";
      } else {
        this.role = "관리자";
      }
    }
  }
};
</script>