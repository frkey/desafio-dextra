import DashView from './components/Dash.vue'
import ForbbidenView from './components/403.vue'
import NotFoundView from './components/404.vue'
import ErrorView from './components/500.vue'

// Import Views - Dash
import DashboardView from './components/views/Dashboard.vue'
import IngredientView from './components/views/Ingredient.vue'
import BurgerView from './components/views/Burger.vue'
import OrderView from './components/views/Order.vue'

// Routes
const routes = [
  {
    path: '/',
    component: DashView,
    children: [
      {
        path: 'dashboard',
        alias: '',
        component: DashboardView,
        name: 'Dashboard',
        meta: {description: 'Overview of environment'}
      }, {
        path: 'ingredients',
        component: IngredientView,
        name: 'Ingredients',
        meta: {description: 'Ingredients CRUD'}
      }, {
        path: 'burgers',
        component: BurgerView,
        name: 'Burgers',
        meta: {description: 'Burgers CRUD'}
      }, {
        path: 'orders',
        component: OrderView,
        name: 'Orders',
        meta: {description: 'Orders CRUD'}
      }
    ]
  }, {
    path: '/forbbiden',
    component: ForbbidenView
  }, {
    path: '/error',
    component: ErrorView
  }, {
    // not found handler
    path: '*',
    component: NotFoundView
  }
]

export default routes
