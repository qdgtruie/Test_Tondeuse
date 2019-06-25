Scenario: typical test

Given a garden of 5 by 5
And a mower at position 1 2 N with instructions GAGAGAGAA
And a mower at position 3 3 E with instructions AADAADADDA
When the controller run the mowers
Then Mower number 1 should be at final position 1 3 N
And Mower number 2 should be at final position 5 1 E


Scenario: Colision test

Given a garden of 2 by 2
And a mower at position 0 0 N with instructions A 
And a mower at position 1 1 W with instructions A
When the controller run the mowers
Then Mower number 1 should be at final position 0 1 N 
And Mower number 2 should be at final position 1 1 W
