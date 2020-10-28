<template>
  <v-container fill-height style="max-width:450px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-card class="mx-auto" tile>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>
                <h3>마이페이지</h3>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title>아이디</v-list-item-title>
              <v-list-item-subtitle>{{userInfo.uid}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title>이름</v-list-item-title>
              <v-list-item-subtitle>{{userInfo.name}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-btn
            router
            :to="{ name: 'updateuserinfo' }"
            depressed
            block
            large
            color="primary"
          >회원정보수정</v-btn>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

    
<script>
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["userInfo"])
  },
  methods: {
    modify() {
      console.log("수정페이지");
    }
  }
};
</script>