<template>
    <div class="container">
      <div class="content">
        <p v-if="quote" class="quote-text">{{ quote.text }}</p>
        <p v-else>Loading...</p>
        <button @click="fetchRandomQuote" class="quote-button">Get New Quote</button>
  
        <div v-if="quote" class="mood-selection">
          <label>
            <input type="radio" value="good" v-model="userMood" /> Good
          </label>
          <label>
            <input type="radio" value="neutral" v-model="userMood" /> Neutral
          </label>
          <label>
            <input type="radio" value="bad" v-model="userMood" /> Bad
          </label>
          <button @click="submitMood" class="mood-button">Submit Mood</button>
        </div>
  
        <div class="add-quote-form">
          <input type="text" v-model="newQuoteText" placeholder="Enter new quote" />
          <button @click="addQuote" class="add-quote-button">Add Quote</button>
          <p v-if="addQuoteMessage" class="add-quote-message">{{ addQuoteMessage }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        quote: null,
        userMood: null,
        newQuoteText: '',
        addQuoteMessage: '',
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
            this.userMood = null;
          })
          .catch(error => {
            console.error('Error fetching quote:', error);
          });
      },
      submitMood() {
        if (!this.userMood) {
          alert("Please select a mood.");
          return;
        }
        axios.post('http://localhost:8082/api/mood', {
          quoteId: this.quote.id,
          mood: this.userMood,
        })
        .then(response => {
          console.log('Mood submitted:', response.data);
          alert("Mood submitted successfully!");
        })
        .catch(error => {
          console.error('Error submitting mood:', error);
          alert("Failed to submit mood.");
        });
      },
      addQuote() {
        axios.post('http://localhost:8082/api/quotes', {
          text: this.newQuoteText,
        })
        .then(response => {
          console.log('Quote added:', response.data);
          this.addQuoteMessage = 'Quote added successfully!';
          this.newQuoteText = '';
          this.fetchRandomQuote();
        })
        .catch(error => {
          console.error('Error adding quote:', error);
          this.addQuoteMessage = 'Failed to add quote.';
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
  
  .mood-selection {
    margin-top: 20px;
  }
  
  .mood-button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    margin-left: 10px;
  }
  
  .add-quote-form {
    margin-top: 20px;
  }
  
  .add-quote-form input {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-right: 10px;
  }
  
  .add-quote-button {
    background-color: #2196F3;
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    margin-left: 10px;
  }
  
  .add-quote-message {
    margin-top: 10px;
    color: #4CAF50; /* Green for success, red for error */
  }
  </style>