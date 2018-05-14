import DashView from './components/Dash.vue'
import NotFoundView from './components/404.vue'

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
    // not found handler
    path: '*',
    component: NotFoundView
  }
]

export default routes
