<template>
  <div class="container">
    <div class="content">
      <p v-if="quote" class="quote-text">{{ quote.text }}</p>
      <p v-else>Loading...</p>
      <button @click="fetchRandomQuote" class="quote-button" :disabled="loadingQuote">
        <span v-if="loadingQuote">Loading...</span>
        <span v-else>Get New Quote</span>
      </button>

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
        <button @click="submitMood" class="mood-button" :disabled="loadingMood">
          <span v-if="loadingMood">Loading...</span>
          <span v-else>Submit Mood</span>
        </button>
        <p v-if="moodMessage" class="message" :class="moodMessageClass">{{ moodMessage }}</p>
      </div>

      <div class="add-quote-form">
        <input type="text" v-model="newQuoteText" placeholder="Enter new quote" />
        <button @click="addQuote" class="add-quote-button" :disabled="loadingAddQuote">
          <span v-if="loadingAddQuote">Loading...</span>
          <span v-else>Add Quote</span>
        </button>
        <p v-if="addQuoteMessage" class="message" :class="addQuoteMessageClass">{{ addQuoteMessage }}</p>
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
      moodMessage: '',
      loadingQuote: false,
      loadingMood: false,
      loadingAddQuote: false,
      moodMessageClass: '',
      addQuoteMessageClass: '',
    };
  },
  mounted() {
    this.fetchRandomQuote();
  },
  methods: {
    fetchRandomQuote() {
      this.loadingQuote = true;
      axios
        .get('http://localhost:8082/api/quotes/random')
        .then((response) => {
          this.quote = response.data;
          this.userMood = null;
        })
        .catch((error) => {
          console.error('Error fetching quote:', error);
        })
        .finally(() => {
          this.loadingQuote = false;
        });
    },
    submitMood() {
      if (!this.userMood) {
        this.moodMessage = 'Please select a mood.';
        this.moodMessageClass = 'error';
        return;
      }
      this.loadingMood = true;
      this.moodMessage = ''; // Clear previous message
      this.moodMessageClass = '';
      axios
        .post('http://localhost:8082/api/mood', {
          quoteId: this.quote.id,
          mood: this.userMood,
        })
        .then(() => {
          this.moodMessage = 'Mood submitted successfully!';
          this.moodMessageClass = 'success';
        })
        .catch((error) => {
          console.error('Error submitting mood:', error);
          this.moodMessage = 'Failed to submit mood.';
          this.moodMessageClass = 'error';
        })
        .finally(() => {
          this.loadingMood = false;
        });
    },
    addQuote() {
      this.loadingAddQuote = true;
      this.addQuoteMessage = ''; // Clear previous message
      this.addQuoteMessageClass = '';
      axios
        .post('http://localhost:8082/api/quotes', {
          text: this.newQuoteText,
        })
        .then(() => {
          this.addQuoteMessage = 'Quote added successfully!';
          this.addQuoteMessageClass = 'success';
          this.newQuoteText = '';
          this.fetchRandomQuote();
        })
        .catch((error) => {
          console.error('Error adding quote:', error);
          this.addQuoteMessage = 'Failed to add quote.';
          this.addQuoteMessageClass = 'error';
        })
        .finally(() => {
          this.loadingAddQuote = false;
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

.message {
  margin-top: 10px;
  font-weight: bold;
}

.success {
  color: green;
}

.error {
  color: red;
}
</style>