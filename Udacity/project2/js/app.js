/*
 * Create a list that holds all of your cards
 */


/*
 * Display the cards on the page
 *   - shuffle the list of cards using the provided "shuffle" method below
 *   - loop through each card and create its HTML
 *   - add each card's HTML to the page
 */

// Shuffle function from http://stackoverflow.com/a/2450976
function shuffle(array) {
    var currentIndex = array.length, temporaryValue, randomIndex;

    while (currentIndex !== 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }

    return array;
}
//get all cards
let oldcardsArray=document.querySelectorAll('.card');
// shuffle cards
let newCardsArray=shuffle(Array.from(oldcardsArray));
//to save opened cards
let card1=null;
let card2=null;
let moveCounter=0;
let moveElement=document.querySelector('.moves');
let sec=0;
let timer;
let matchCounter=0;
let userTime;
let gameoverTimer;
let gameOverFLag=false;
let ratingMs="3.0";
//
function checkCard(card){
  //save first card
  if (card1==null) {
    moveCounter++;
    card.classList.add('open');
    card.classList.add('show');
    card1=card;
    //save second card and make sure it does not equal to the first one
  }else if (card2==null&&card!=card1) {
    moveCounter++;
    card.classList.add('open');
    card.classList.add('show');
    card2=card;
  }
  //increase moves count
  moveElement.innerHTML=moveCounter;
  //check number of Moves
  checkStarRating(moveCounter);
  //if we have two cards then we can compare if they match or not
  if (card1!=null&&card2!=null)
  {  if (card1.firstElementChild.className==card2.firstElementChild.className) {
      matchCounter++;
      if (matchCounter==8) {
        //winning case
      clearInterval(timer);
      userTime=document.querySelector('.timer').textContent;
      document.querySelector('.user-time').innerHTML="your time "+userTime+"</br> your rate "+ratingMsg;
      document.querySelector('.congrats-title').innerHTML="Congratulation";
      document.querySelector('.winner-icon').src="img/medal.png";
      document.querySelector('.winner-box').style.display="flex";
      gameOverFLag=true;
      }
      card1.classList.remove('open');
      card1.classList.remove('show');
      card2.classList.remove('open');
      card2.classList.remove('show');
      card1.classList.add('match');
      card2.classList.add('match');
      card1=null;
      card2=null;
        }else {
          setTimeout(function(){
            if (card1!=null) {
              card1.classList.remove('open');
              card1.classList.remove('show');
            }
            if (card2!=null) {
            card2.classList.remove('open');
            card2.classList.remove('show');
            }
            card1=null;
            card2=null;},1000);
        }
    }
}
//append the cards to the ul in html
function appendCards(){
let mainListOfCards=document.querySelector('.deck');
for (let card of newCardsArray) {
  //make sure that card close
  card.classList.remove('open');
  card.classList.remove('show');
  card.classList.remove('match');
  mainListOfCards.appendChild(card);
  //adding listener to each card
  card.addEventListener('click',function(){
    checkCard(card);});
}
}
appendCards();
//timer
function pad(number){
  if (number>9)
  return number;
  else
  return "0"+number;
}
function startTimer(){
  timer=setInterval(function(){
  document.getElementById('sec').innerHTML=pad(++sec % 60);
  document.getElementById('min').innerHTML=pad(parseInt(sec / 60,10));
},1000);
}
function gameOverCountDown()
{
  //losing case
  gameoverTimer=setTimeout(function(){
    document.querySelector('.winner-icon').src="img/rekt.png";
    document.querySelector('.user-time').innerHTML="sorry,, timeout try again :(";
    document.querySelector('.congrats-title').innerHTML="Game Over";
    document.querySelector('.winner-box').style.display="flex";
    clearInterval(timer);
    gameOverFLag=true;
  },120000);
}
startTimer();
gameOverCountDown();
//restart the Game
function restartGame(){
  card1=null;
  card2=null;
  moveCounter=0;
  moveElement.innerHTML=moveCounter;
  oldcardsArray=document.querySelectorAll('.card');
  newCardsArray=shuffle(Array.from(oldcardsArray));
  appendCards();
  document.getElementById('sec').innerHTML="00";
  document.getElementById('min').innerHTML="00";
  sec=0;
  matchCounter=0;
  document.querySelector('.winner-box').style.display="none";
  document.querySelector('#star1').className="fa fa-star";
  document.querySelector('#star2').className="fa fa-star";
  document.querySelector('#star3').className="fa fa-star";
  ratingMsg="3.0"
  clearTimeout(gameoverTimer);
  gameoverTimer=0;
  if (gameOverFLag) {
    startTimer();
    gameOverFLag=false;
  }
  gameOverCountDown();

}
//listener to restart icon
document.querySelector('.restart').addEventListener('click',function(){restartGame();});
function checkStarRating(move)
{
  switch (move) {
    case 25:
      document.querySelector('#star3').className="fa fa-star-half-o";
      ratingMsg="2.5";
      break;
    case 32:
      document.querySelector('#star3').className="fa fa-star-o";
      ratingMsg="2.0";
      break;
    case 39:
      document.querySelector('#star2').className="fa fa-star-half-o";
      ratingMsg="1.5";
      break;
    case 46:
      document.querySelector('#star2').className="fa fa-star-o";
      ratingMsg="1.0";
      break;
    case 53:
      document.querySelector('#star1').className="fa fa-star-half-o";
      ratingMsg="0.5";
      break;
    case 60:
      document.querySelector('#star1').className="fa fa-star-o";
      ratingMsg="0.0";
      break;
    default:
    document.querySelector('#star1').className="fa fa-star";
  }
}
/*
 * set up the event listener for a card. If a card is clicked:
 *  - display the card's symbol (put this functionality in another function that you call from this one)
 *  - add the card to a *list* of "open" cards (put this functionality in another function that you call from this one)
 *  - if the list already has another card, check to see if the two cards match
 *    + if the cards do match, lock the cards in the open position (put this functionality in another function that you call from this one)
 *    + if the cards do not match, remove the cards from the list and hide the card's symbol (put this functionality in another function that you call from this one)
 *    + increment the move counter and display it on the page (put this functionality in another function that you call from this one)
 *    + if all cards have matched, display a message with the final score (put this functionality in another function that you call from this one)
 */
