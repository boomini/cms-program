<template>
  <v-container fill-height style="max-width:900px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <section class="srch_box" v-on:keyup.enter="searchClick">
                    <v-col
          cols="12"
          sm="8"
        >
          <v-text-field
            label="제목 or 내용"
            solo
            v-model="search.title"
          ></v-text-field>
          
        </v-col>
            <button class="btn srch_btn" @click="searchClick"><i></i>검색</button>
        </section>
        <v-simple-table>
          <template v-slot:default>
            
            <thead>
              <tr>
                <th width="10" class="text-left">
                  글쓴이
                </th>
                <th class="text-left">
                  글제목
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="item in postList"
                :key="item.post_id"
                @click="detail(item)"
              >
                <td>{{ item.author }}</td>
                <td>{{ item.title }}</td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <div class="my-2" align="right">
                    <v-btn depressed small color="primary" @click="write"
                      >글작성</v-btn
                    >
                  </div>
                </td>
              </tr>
              <tr>
                <td v-if="postList.length==0" colspan="7"> 검색 결과가 없습니다. </td>
              </tr>
              
            </tbody>
          </template>
          
        </v-simple-table>
         
      </v-flex>
       <page-component :pageData="page" @onPage="pageClick"></page-component>
    </v-layout>
    
  </v-container>
  
</template>


<script>
import pageComponent from "@/components/board/page"
import { mapActions } from "vuex";
import axios from "axios";
export default {
    components: {
        pageComponent
    },
  data() {
    return {
      page:{
        page:1,
        total:1,
        count:3
      },
      search:{
        content:"",
        title:""
      },
      boardName: "free",
      postList:[]
    };
  },
  created(){
    this.pageLoadHandler()
  },

  methods: {
    ...mapActions(["postWrite"]),
    write() {
      this.$router.push({ name: "boardwrite" });
    },
    detail(item) {
      this.$router.push({
        name: "boarddetail",
        params: {
          post_id : item.post_id,
          author: item.author,
          title: item.title,
          content: item.content
        }
      });
    },
    pageClick(pageNum){
      this.page.page=pageNum;
      console.log(pageNum)
      this.pageLoadHandler();
    },
    searchClick(){
      this.search.content=this.search.title;
      this.pageLoadHandler();
    },
    pageLoadHandler(){
      var url = "http://localhost:3500/v1/board/" + this.boardName + "/posts";
            axios
                .get(url, { params: { content : this.search.content,
                                      title : this.search.title,
                                      page: this.page.page, 
                                      count: this.page.count } }, {
                    headers: { 'x-accept-type': 'operator' }
                })
                .then(res => {
  
                    console.log(res)
                    console.log(res.data.list)
                    console.log(res.data.page)
                    this.postList = res.data.list;
                    this.page=res.data.page;

                })
                .catch(err => {
                    console.log(err)
                })
        },
    }
  
};
</script>