DROP TABLE publicacion CASCADE CONSTRAINTS;
DROP TABLE resenia CASCADE CONSTRAINTS;

CREATE TABLE publicacion (
    id_publicacion  NUMBER(8) GENERATED ALWAYS AS IDENTITY
                          MAXVALUE 99999999
                          INCREMENT BY 1 
                          START WITH 1,
    usedId          NUMBER(4) NOT NULL,
    title           VARCHAR2(100) NOT NULL,
    descripcion     VARCHAR2(400) NOT NULL
);

ALTER TABLE publicacion ADD CONSTRAINT publicacion_pk PRIMARY KEY ( id_publicacion );

CREATE TABLE resenia (
    id_resenia      NUMBER(8) GENERATED ALWAYS AS IDENTITY
                          MAXVALUE 99999999
                          INCREMENT BY 1 
                          START WITH 1,
    id_publicacion  NUMBER(8) NOT NULL,
    usedId          NUMBER(4) NOT NULL,
    title           VARCHAR2(150) NOT NULL,
    descripcion     VARCHAR2(500) NOT NULL,
    stars           NUMBER(1)
);

ALTER TABLE resenia ADD CONSTRAINT resenia_pk PRIMARY KEY ( id_resenia );

ALTER TABLE resenia
  ADD CONSTRAINT resenia_publicacion_fk FOREIGN KEY ( id_publicacion )
    REFERENCES publicacion ( id_publicacion );

INSERT INTO publicacion VALUES (DEFAULT, 654, 'La Finestra', 'CUISINES : Italian, Pizza, Mediterranean, Neapolitan, Campania, Tuscan, Romana, Lazio, Sicilian, Central-Italian, Southern-Italian');
INSERT INTO resenia VALUES (DEFAULT, 1, 111, 'Awesome italian restaurant in Santiago !', 'If you’re looking for Italian food in Santiago, you have to go to La Finestra. Located in Ñuñoa, with a lovely garden hidden behind the entrance, you’ll enjoy a nice moment with your friends or family, discovering typical food and drinks from Italy.', 5);
INSERT INTO resenia VALUES (DEFAULT, 1, 356, 'Loved this place! Very cool vibe, setting. Authentic Italian pizza.', 'I love a place where the owner is passionate about their food and their craft. If you go here, search out the owner and chat with him. Great guy from Italy, very passionate about the customer experience, the food and the venue. ', 5);
INSERT INTO resenia VALUES (DEFAULT, 1, 763, 'La peggior pizza!', 'For the first time in xx years I did not finish the pizza ... my favorite food... they don’t have a brick oven, but that’s not the reason. The though is bitter and mostly without flavor! A very disappointing experience.', 1);
INSERT INTO resenia VALUES (DEFAULT, 1, 953, 'Delicious but I want more', 'This place is worth going to Nuñoa for. The only thing I would say is that I would love to have bigger portion sizes. Had the gorgonzola gnocchi. Delicious.', 4);
INSERT INTO resenia VALUES (DEFAULT, 1, 523, 'The pizza is hardly eatable!', 'I have visited this place several times with variable success. The pasta is generally good but last time it was overcooked and without any taste. What was really bad was the pizza. I ordered one with mozzarella di buffala.', 1);
INSERT INTO resenia VALUES (DEFAULT, 1, 423, 'Excellent food, poor service', 'We were pleasantly surprised by the reviews on TripAdvisor to find out that the hole-in-the-wall entrance that we thought was a crummy place was in reality a beautiful restaurant inside. The pizza and pasta we ordered was delicious and the menu had a large and good selection. The problem was the service. ', 3);
    
INSERT INTO publicacion VALUES (DEFAULT, 234, 'La Cabrera Chile Alonso', 'CUISINES : Argentinean, Steakhouse');
INSERT INTO resenia VALUES (DEFAULT, 2, 465, 'DONT GO!', 'The Ritz recommended this restaurant. Our waitress didnt listen to a thing we said. The drinks were wrong, and the meal came all at the same time. It didnt even fit on the table. My husband was hungry for a steak', 1);
INSERT INTO resenia VALUES (DEFAULT, 2, 327, 'Charred steak, screwed up scores', 'Few disappointments rival the impact of hyper-expectations colliding with a starkly different reality during restaurant visits. A dining establishment touted as one of the premier steakhouses globally (included in the prestigious list of the top 101 steakhouses worldwide, prominently displayed at the entrance) serves a charred (!!) steak that, in addition, missed the mark on the desired level of doneness', 2);
INSERT INTO resenia VALUES (DEFAULT, 2, 467, 'Great Service!', 'The service was very good. The steaks were just okay. A bit tough and too fatty. A bit disappointing considering the high ratings. We expected a better quality meat.', 4);
INSERT INTO resenia VALUES (DEFAULT, 2, 234, 'Fantastic stunning beautiful steaks', 'Fantastic stunning food ginormous steaks…. waiter Ricardo was brilliant and very helpful. Will definitely come back!', 5);
INSERT INTO resenia VALUES (DEFAULT, 2, 156, 'Fantastic food and fantastic staff', 'Great food, great ambience, great waiter. Special thanks to Patricio. Really nice and most funniest guy. We really enjoyed the evening with him.', 5);

INSERT INTO publicacion VALUES (DEFAULT, 576, 'Alto Aji Seco', 'CUISINES : Peruvian, Latin, Seafood');
INSERT INTO resenia VALUES (DEFAULT, 3, 943, 'Excellent, but if it cost a bit less Id give it 5 stars', 'Excellent Peruvian restaurant in Las Condes. Lots of parking available. There arent any other restaurants around that area so its definitely a destination place. Nicely remodeled and decorated, friendly and courteous staff, great food served in generous portions. Its one of the better Peruvian restaurants we have tried in Chile so far. Better value than Tanta or La Mar.', 4);
INSERT INTO resenia VALUES (DEFAULT, 3, 763, 'Really good peruvian food in Santiago', 'I really enjoyed this restaurant. it is in the end of las condes almost going to el Arrayan o plaza san enrique, the place is nice, has a piano, a terrace and a elevator. we tried the barbequed octopus, and the ceviche trio for starters, they where outstanding. The pisco sour was good also, but the dish that i enjoyed the most was the Lomo mary tierra (Lomo land and ocean).', 4);
INSERT INTO resenia VALUES (DEFAULT, 3, 362, 'Good food and good service.', 'This is Peruin cuisine, the food is good so as the atmersfere and service. It is not cheap for the locals, but you pay for what you enjoy.v', 4);
INSERT INTO resenia VALUES (DEFAULT, 3, 235, 'Good food, poor service, high prices', 'This restaurant serves refined peruvian food, but at very high prices, I domn´t think it is a good value-price place to go. Although this restaurant doesn´t have a closing hour (the same as almost every other restaurant in Chile), as soon as we finished with our dessert, they started to do things to get us out of tghe rerstaurant as soon as possible:very loud music, dim the lights and stopped the central heating so that we would be cold.', 2);
INSERT INTO resenia VALUES (DEFAULT, 3, 635, 'Delicious', 'Great service, great food, great ambiance, and wonderful ceviches and pisco sours. It is a great restaurant to go, with family or just couples...', 5);

INSERT INTO publicacion VALUES (DEFAULT, 875, 'Tanta', 'CUISINES : Peruvian, Latin, Seafood');
INSERT INTO resenia VALUES (DEFAULT, 4, 764, 'Average food and service', 'Big fan of Gaston Acurio and his restaurants but this one doesn’t seem to be at par. It’s more of a production line version of his restaurants. The service was just okay. Two items in the menu we asked for weren’t available.', 3);
INSERT INTO resenia VALUES (DEFAULT, 4, 936, 'Poor service. Orders take too long. Better places nearby. Rude waiters. I do not recommend this place at all!', 'Poor service. Orders takes too long. Waiters are rude. Better places nearby, just look around! I don’t recommend this place at all!', 1);
INSERT INTO resenia VALUES (DEFAULT, 4, 724, 'Great Peruvian food, but a bit pricey', 'A Peruvian friend recommended me this place so I had to try it. Its located inside a food court in a mall, but its big enough that it isnt really a problem. Its really great; the food was excellent and the service also good.', 4);
INSERT INTO resenia VALUES (DEFAULT, 4, 254, 'delicious food and lovely appetizer', 'amazing ceviche. We had very nice friendly service and a helpful waiter even though our spanish was minimal', 5);
INSERT INTO resenia VALUES (DEFAULT, 4, 736, 'Fantastic Octupus and Pisco', 'Had a great lunch today with my business associates - had two Octopus with a great light spiced sauce and and superb chicken dish with equivalent sauce.', 4);


COMMIT;