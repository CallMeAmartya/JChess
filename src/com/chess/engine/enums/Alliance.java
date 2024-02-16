package com.chess.engine.enums;

import com.chess.engine.player.Player;

public enum Alliance {
  BLACK {
    @Override
    public int getDirection() {
      return 1;
    }

    @Override
    public Player getPlayer(Player whitePlayer, Player blackPlayer) {
      return blackPlayer;
    }
  },
  WHITE {
    @Override
    public int getDirection() {
      return -1;
    }

    @Override
    public Player getPlayer(Player whitePlayer, Player blackPlayer) {
      return whitePlayer;
    }
  };

  public abstract int getDirection();

  public abstract Player getPlayer(Player whitePlayer, Player blackPlayer);
}
