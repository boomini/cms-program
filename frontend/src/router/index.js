import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store"

Vue.use(VueRouter);

const rejectAuthUser = (to, from, next) => {
    if (store.state.isLogin === true) {
        //이미 로그인 된 유저니까 막아야.
        alert("이미 로그인을 하였습니다.")
        next("/")
    } else {
        next()
    }
}
const onlyAuthUser = (to, from, next) => {
    if (store.state.isLogin === false) {
        //아직 로그인이 안 된 유저니까 막아야.
        alert("로그인이 필요한 기능입니다")
        next("/signin")
    } else {
        next()
    }
}
const routes = [{
        path: "/",
        name: "home",
        component: () =>
            import ( /* webpackChunkName: "about" */ "../views/Home.vue")
    },
    {
        path: "/signin",
        name: "signin",
        beforeEnter: rejectAuthUser,
        component: () =>
            import ( /* webpackChunkName: "about" */ "../views/Login.vue")
    },
    {
        path: "/mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () =>
            import ( /* webpackChunkName: "mypage" */ "../views/Mypage.vue")
    },
    {
        path: "/userlist",
        name: "userlist",
        beforeEnter: onlyAuthUser,
        component: () =>
            import ( /* webpackChunkName: "mypage" */ "../views/UserList.vue")
    },
    {
        path: "/signup",
        name: "signup",
        component: () =>
            import ( /* webpackChunkName: "mypage" */ "../views/SignUp.vue")
    },
    {
        path: "/updateuserinfo",
        name: "updateuserinfo",
        beforeEnter: onlyAuthUser,
        component: () =>
            import ( /* webpackChunkName: "mypage" */ "../views/UpdateUserInfo.vue")
    },
    {
        path: "/sociallogin",
        name: "sociallogin",
        component: () =>
            import ( /* webpackChunkName: "mypage" */ "../views/SocialLogin.vue")
    },

];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;