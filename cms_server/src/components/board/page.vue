<template>
    <section class="paging">
        <a class="pagingFirst" 
        @click = "prevPage()"
        :disabled = "pageData.page == 1">처음</a>
            <ul class="pagingList">
                <li 
                v-for = "page in countInPages" 
                :key = "page"
                :class = "(startPage-1)+page === pageData.page ? 'active' : null"
                @click = "selectPage( (startPage-1)+page )"
                > {{(startPage-1) + page}} </li>
            </ul>
        <a class="pagingLast" @click = "nextPage()">끝</a>
    </section>
</template>
<script>
export default {
    name: 'PagingView',
    props: {
        pageData: {
            type: Object,
            default () {
                return {
                    total: 60,
                    page: 1,
                    count: 10
                }
            }
        },
        defaultPages: {
            type: Number,
            default: 10
        }
    },
    computed: {
        startPage: function () {
            let value = (Math.floor((this.pageData.page-1) / this.defaultPages) * this.defaultPages) + 1
            return value
        },
        totalPage: function () {
            return Math.floor(this.pageData.total / this.pageData.count) + ((this.pageData.total % this.pageData.count) == 0 ? 0 : 1)
        },
        countInPages: function () {
            let currPages = this.totalPage - (this.startPage-1)
            if (currPages < this.defaultPages) {
                return currPages
            } else {
                return this.defaultPages
            }
        }
    },
    methods: {
        selectPage: function (page) {
            this.pageData.page = page
            this.$emit("onPage",  page)
        },

        prevPage: function () {
            if (this.pageData.page == 1) {
                return
            }
            
            this.selectPage(this.pageData.page - 1)
        },

        nextPage: function () {
            if (this.pageData.page == this.totalPage) {
                return
            }
            
            this.selectPage(this.pageData.page + 1)
        }
    }
}
</script>