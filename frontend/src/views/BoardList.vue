<template>
  <v-container fill-height style="max-width:900px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">
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
            </tbody>
          </template>
        </v-simple-table>
      </v-flex>
    </v-layout>
  </v-container>
</template>


<script>
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      author: null,
      title: null,
      content: null
    };
  },
  computed: {
    ...mapState(["postList"])
  },
  methods: {
    ...mapActions(["getPostList", "postWrite"]),
    write() {
      this.$router.push({ name: "boardwrite" });
    },
    detail(item) {
      this.$router.push({
        name: "boarddetail",
        params: {
          author: item.author,
          title: item.title,
          content: item.content
        }
      });
    }
  }
};
</script>