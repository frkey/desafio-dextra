<template>
  <section class="content">
    <sweet-modal ref="modal">
      Teste
    </sweet-modal>
    <div class="row">
      <div class="col-sm-12">
        <div class="panel panel-info">
          <div class="panel-heading">Burgers</div>
          <div v-on:click="openModal" class="panel-body">
            <datasource
              language="pt-BR"
              :pagination="pagination"
              :table-data="response"
              :columns="columns"
              :actions="actions"></datasource>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { SweetModal } from 'sweet-modal-vue'
import Datasource from 'vue-datasource'
import messageService from '../../services/messageService'
import burgerBackend from '../../apis/burgerBackend'

export default {
  name: 'Repository',
  components: {
    SweetModal,
    Datasource
  },
  data() {
    return {
      response: [],
      pagination: {
        total: 0,
        perpage: 15,
        last_page: 1,
        current_page: 1,
        from: 1,
        to: 0
      },
      actions: [],
      columns: [
        {
          name: 'Name',
          key: 'name'
        },
        {
          name: 'Ingredients',
          key: 'ingredients',
          render(value) {
            // Render callback
            return value.map(function(v) { return v['name'] }).toString()
          }
        }
      ]
    }
  },
  methods: {
    openModal(e) {
      if (e.target.nodeName == 'I') {
        if (
          e.target.id !== undefined &&
          e.target.id !== null &&
          e.target.id !== '' &&
          e.target.id !== ' '
        ) {
          console.log(e.target.id)
          // this.get
          this.$refs.modal.open()
        }
      }
    },
    loadBurgers() {
      var _self = this
      burgerBackend.loadBurgers(
        response => {
          _self.response = response.data
          _self.pagination.total = response.data.length
          _self.pagination.to = response.data.length
        },
        error => {
          if (error.response.data) {
            messageService.errorMessage(_self, error.response.data.message)
          } else {
            messageService.errorMessage(_self, error.response)
          }
        }
      )
    }
  },
  mounted() {
    this.loadBurgers()
  }
}
</script>

<style>
.img-circle {
  height: 120px;
}
.panel-info-header {
  padding-top: 1%;
}
.align-left {
  text-align: left;
  font-weight: bold;
}
.api-operations {
  height: 20px;
}

.api-operations:hover {
  cursor: pointer;
}

.text-center {
  text-align: center;
}

.alert {
  font-size: 100%;
  word-break: break-all;
}

.pull-right {
  margin-left: 2px;
}

i:hover {
  cursor: pointer;
}
</style>
