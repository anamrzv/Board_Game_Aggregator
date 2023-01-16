import Vue from 'vue'
import Router from 'vue-router'

/*боковая панель*/
import { BootstrapVue} from 'bootstrap-vue'

// Импортируйте файлы CSS Bootstrap и BootstrapVue (порядок важен)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// Сделайте BootstrapVue доступным для всего проекта

/*боковая панель*/

import Main from "./views/Main.vue";
import Login from "./views/Login.vue";
import NotFound from "./views/NotFound";

import User from "./views/User.vue";
import Admin from "./views/Admin.vue";
import Shop from "./views/Shop.vue";
import Cart from "./views/Cart.vue";
import Favorite from "./views/Favorite.vue";
import Forum from "./views/Forum.vue";

Vue.use(BootstrapVue);
Vue.use(Router);

const routes = [

    {
        path: '/game_aggregator',
        name: 'auth-page',
        component: Login
    },
    {
        path: '/game_aggregator/auth/user',
        name: 'user-page',
        component: User
    },
    {
        path: '/game_aggregator/auth/admin',
        name: 'admin-page',
        component: Admin
    },
    {
        path: '/game_aggregator/auth/shop',
        name: 'shop-page',
        component: Shop,
    },
    {
        path: '/game_aggregator/game',
        name: 'main',
        component: Main,
    },
    {
        path: '/game_aggregator/user/cart',
        name: 'cart-page',
        component: Cart,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem("jwt") !== 'null') next();
            else next({
                name: 'error-page-app',
            });
        }
    },
    {
        path: '/game_aggregator/user/fav',
        name: 'favorite-page',
        component: Favorite,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem("jwt") !== 'null') next();
            else next({
                name: 'error-page-app',
            });
        }
    },
    {
        path: '/game_aggregator/forum',
        name: 'forum-page',
        component: Forum,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem("jwt") !== 'null') next();
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
            errorMsg: "Для доступа требуется авторизация."
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




