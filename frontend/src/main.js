import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    beforeCreate() {
        this.$store.dispatch('getMemberInfo')
    },
    vuetify,
    render: h => h(App)
}).$mount("#app");