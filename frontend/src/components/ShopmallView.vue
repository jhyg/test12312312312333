
<template>

    <v-data-table
        :headers="headers"
        :items="shopmall"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'ShopmallView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "orderId", value: "orderId" },
                { text: "productId", value: "productId" },
                { text: "qty", value: "qty" },
                { text: "deliveryId", value: "deliveryId" },
            ],
            shopmall : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/shopmalls'))

            temp.data._embedded.shopmalls.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.shopmall = temp.data._embedded.shopmalls;
        },
        methods: {
        }
    }
</script>

