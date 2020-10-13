<template>
  <v-container fill-height style="max-width:900px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-toolbar flat dense>
          <v-toolbar-title>글작성</v-toolbar-title>
        </v-toolbar>
        <div class="pa-3">
          <v-text-field v-model="title" label="제목"></v-text-field>
          <v-text-field v-model="author" label="작성자"></v-text-field>
          <v-textarea
            solo
            v-model="content"
            rows="10"
            row-height="500"
            name="input-20-4"
            label="내용"
          ></v-textarea>
          <v-row align="center" justify="space-around">
            <v-btn
              router-link
              :to="{ name: 'boardlist' }"
              depressed
              color="primary"
              >목록</v-btn
            >
            <v-btn
              @click="
                postWrite({
                  boardName,
                  title,
                  author,
                  content
                })
              "
              depressed
              color="primary"
              >글작성</v-btn
            >
          </v-row>

          <div class="my-2"></div>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapActions, mapState } from "vuex";
export default {
  data() {
    return {
      title: null,
      author: null,
      content: null,
      boardName: "free"
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  mounted() {},
  methods: {
    ...mapActions(["postWrite"])
  }
};
</script>
