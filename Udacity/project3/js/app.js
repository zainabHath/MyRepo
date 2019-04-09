// Enemy Class
var Enemy = function(x,y,speed) {
    // Variables applied to each of our instances go here,
    this.x=x;
    this.y=y;
    this.speed=speed;
    // The image/sprite for our enemies, this uses
    this.sprite = 'images/enemy-bug.png';
};
// Update the enemy's position, required method for game
Enemy.prototype.update = function(dt) {
    this.x=this.x+(this.speed*dt);
    if (this.x>500) {
      this.x=-100;
    }
    //collision case
    if (player.x>=(this.x-50)&&player.x<=(this.x+50))
     {if (player.y>=(this.y-50)&&player.y<=(this.y+50)) {
       collision();
     }
    }

};

// Draw the enemy on the screen, required method for game
Enemy.prototype.render = function() {
    ctx.drawImage(Resources.get(this.sprite), this.x, this.y);
};

//Player Class
var Player = function() {
    // Variables applied to each of our instances go here,
    this.x=200;
    this.y=440;
    this.lives=document.querySelector("#lives");
    this.level=document.querySelector("#level");
    this.livesNum=5;
    this.levelNum=1;
    // The image/sprite for our player, this uses
    this.sprite = 'images/char-cat-girl.png';

};
Player.prototype.handleInput=function(key){
  //handle movement cases
  if (key=='up'&&this.y>0) {
    this.y=this.y-25;
  }else if (key=='down'&&this.y<440) {
    this.y=this.y+25;
  } else if (key=='left'&&this.x>0) {
    this.x=this.x-25;
  }else if (key=='right'&&this.x<400) {
    this.x=this.x+25;
  }
}
// Update the player's position, required method for game
Player.prototype.update = function(dt) {
  //win case
  if (this.y==-10) {
    winner();
   }
};

// Draw the player on the screen, required method for game
Player.prototype.render = function() {
    ctx.drawImage(Resources.get(this.sprite), this.x, this.y);
};

// Now instantiate your objects.
let enemy1=new Enemy(200,60,30);
let enemy2=new Enemy(100,140,60);
let enemy3=new Enemy(150,230,40);
let enemy4=new Enemy(-100,150,50);
// Place all enemy objects in an array called allEnemies
let allEnemies=[enemy1,enemy2,enemy3,enemy4];
// Place the player object in a variable called player
let player=new Player();
// winning function
function winner()
{
  player.levelNum++;
  player.level.innerHTML="level "+player.levelNum;
  for (enemy of allEnemies) {
    enemy.speed=enemy.speed+50;
  }
  player.x=200;
  player.y=440;
  alert("Good Job you move to level "+player.levelNum);
}
//lossing function
function collision()
{
  if (player.livesNum==0) {
    alert("Game Over, sorry you will start from level 1");
    player.livesNum=5;
    player.lives.innerHTML=player.livesNum;
    for (enemy of allEnemies) {
      enemy.speed=enemy.speed-(50*(player.levelNum-1));
    }
    player.levelNum=1;
    player.level.innerHTML="level "+player.levelNum;
  }else {
    player.livesNum=player.livesNum-1;
    player.lives.innerHTML=player.livesNum;
  }
 player.x=200;
 player.y=440;
}
// This listens for key presses and sends the keys to your
document.addEventListener('keyup', function(e) {
    var allowedKeys = {
        37: 'left',
        38: 'up',
        39: 'right',
        40: 'down'
    };

    player.handleInput(allowedKeys[e.keyCode]);
});
