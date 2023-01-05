import Vue from 'vue'
import Router from 'vue-router'

import Main from "./views/Main.vue";
import Login from "./views/Login.vue";
import NotFound from "./views/NotFound";

import User from "./views/User.vue";
import Admin from "./views/Admin.vue";
import Shop from "./views/Shop.vue";

Vue.use(Router);

const routes = [
    {
        path: '/',
        name: 'default-page',
        component: Login,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem("jwt") !== null) next({name: 'main'});
            else next({name: 'auth-page'});
        }
    },
    {
        path: '/auth',
        name: 'auth-page',
        component: Login
    },
    {
        path: '/auth/user',
        name: 'user-page',
        component: User
    },
    {
        path: '/auth/admin',
        name: 'admin-page',
        component: Admin
    },
    {
        path: '/auth/shop',
        name: 'shop-page',
        component: Shop
    },
    {
        path: '/main',
        name: 'main',
        component: Main,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem("jwt")) next();
            else next({
                name: 'error-page-app',
            });
        }
    },
    {
        path: '/*',
        name: 'error-page',
        component: NotFound,
        props: {
            default: true,
            errorCode: "404",
            errorMsg: "Страницы по этому адресу не существует"
        }
    },
    {
        path: '/error',
        name: 'error-page-app',
        component: NotFound,
        props: {
            default: true,
            errorCode: "401",
            errorMsg: "Для доступа к приложению требуется авторизация."
        }
    },
    {
        path: '/expired',
        name: 'error-page-expired',
        component: NotFound,
        props: {
            default: true,
            errorCode: "401",
            errorMsg: "Ваша сессия закончилась. Повторите вход в систему."
        }
    },


]

export default new Router({
    mode: 'history',
    routes
});




