<template>

    <v-card outlined>
        <v-card-title>
            Shopmall # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="OrderId" v-model="item.orderId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="ProductId" v-model="item.productId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Qty" v-model="item.qty" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="DeliveryId" v-model="item.deliveryId" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'ShopmallViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/shopmalls/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

