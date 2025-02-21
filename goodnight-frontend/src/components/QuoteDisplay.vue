<template>
    <div class="container">
      <div class="content">
        <p v-if="quote" class="quote-text">{{ quote.text }}</p>
        <p v-else>Loading...</p>
        <button @click="fetchRandomQuote" class="quote-button">Get New Quote</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        quote: null,
      };
    },
    mounted() {
      this.fetchRandomQuote();
    },
    methods: {
      fetchRandomQuote() {
        axios.get('http://localhost:8082/api/quotes/random')
          .then(response => {
            this.quote = response.data;
          })
          .catch(error => {
            console.error('Error fetching quote:', error);
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    background-color: #222;
    color: #eee;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  
  .content {
    text-align: center;
    padding: 20px;
  }
  
  .quote-text {
    font-size: 2em;
    margin-bottom: 20px;
  }
  
  .quote-button {
    background-color: #333;
    color: #eee;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 5px;
  }
  </style>