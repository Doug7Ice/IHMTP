package app.workers;

import app.helpers.Choices;

public class Worker implements WorkerItf {

    @Override
    public Choices[][] fill(Choices[][] game) {

        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[0].length; j++) {
                Choices value = Choices.N;
                int random = (int) (Math.random() * 100 % 2);
                switch (random) {
                    case 0:
                        value = Choices.X;
                        break;
                    case 1:
                        value = Choices.O;
                        break;
                }
                game[i][j] = value;
            }
        }
        return game;
    }
}
