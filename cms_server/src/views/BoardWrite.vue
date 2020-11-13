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
          <input type="file" multiple name="" id="" @change="fileUpload($event)"><span class="refer"> *첨부 파일은 최대 5개 등록 가능합니다. 첨부파일의 용량은 10MB를 초과할 수 없습니다.</span>
            <div class="file_list" v-for="(list,index) in fileList" :key="index">
              <p>{{list.name}} <button @click="fileDelete(index)">삭제</button></p>
              </div>
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
      boardName: "free",
      fileList: []
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  mounted() {},
  methods: {
    ...mapActions(["postWrite"]),
    fileUpload(event){
      for(let i=0; i<event.target.files.length; i++){
        if(this.fileList.length<5 && event.target.files[i].size<=10485760){
          this.fileList.push(event.target.files[i])
        }
      }
    },
    fileDelete(index){
      this.fileList.splice(index,1);
    }
  }
};
</script>
