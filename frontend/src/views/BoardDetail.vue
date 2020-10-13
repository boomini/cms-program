<template>
  <section id="container">
    <div id="content" class="service_notice detail">
      <section class="cont_header">
        <h2 class="page_title">공지사항 상세</h2>
        <span class="page_path">HOME &gt; 서비스 관리 &gt; 공지사항 상세</span>
      </section>
      <section class="cont_inner">
        <table style="margin-top: 30px">
          <colgroup>
            <col style="width: 8%" />
            <col />
            <col style="width: 8%" />
            <col />
            <col style="width: 8%" />
            <col />
            <col style="width: 8%" />
            <col />
          </colgroup>
          <tbody>
            <tr>
              <th>제목</th>
              <td colspan="5">>{{ $route.params.title }}</td>
            </tr>
            <tr>
              <th>작성자</th>
              <td>{{ $route.params.author }}</td>
            </tr>
            <tr>
              <th>내용</th>
              <td colspan="7" class="form_inp">
                <p class="qna_textare" v-html="$route.params.content"></p>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="btn_area">
          <v-btn
            depressed
            color="primary"
            router-link
            :to="{ name: 'boardlist' }"
          >
            목록
          </v-btn>
        </div>
      </section>
    </div>
  </section>
</template>


<script>
export default {};
</script>
