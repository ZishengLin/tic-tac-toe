#include "minmax_strategy.h"

int available_moves(int* arr, int* result) {
    int count = 0;
    for (int i = 0; i < 9; i++) {
        if(arr[i] == 0) {
            result[count++] = i;
        }
    }

    return count;
}

int next_best_move(int* arr) {
    int possible_moves[9];
    int available_move_size = available_moves(arr, possible_moves);
    if (available_move_size == 0) {
        return -1;
    }
    else {
        int best_score = -1000;
        int best_move = -1;
        for (int i = 0; i < available_move_size; i++) {
            arr[possible_moves[i]] = SERVER_TYPE;
            int move_score = minmax_strategy(arr, 0, CLIENT_TYPE);
            arr[possible_moves[i]] = EMPTY_TYPE;

            if (move_score > best_score) {
                best_score = move_score;
                best_move = possible_moves[i];
            }
        }
        return best_move;
    }
}


int check_win(int* arr) {
    int check_win_tbl[8][3] = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };
    int win = 0;
    for(int i = 0; i < 8; i++) {
        win = check_win_helper (arr, check_win_tbl[i]);
        if(win != EMPTY_TYPE) {
            break;
        }
    }
    return win;
}

int check_win_helper (int* arr, int* posn) {
    if (arr[posn[0]] == arr[posn[1]] && arr[posn[1]] == arr[posn[2]]) {
        return arr[posn[0]];
    } else {
        return EMPTY_TYPE;
    }
}

int minmax_strategy(int* arr, int depth, int type) {
    // get available moves
    int possible_moves[9];
    int available_move_size = available_moves(arr, possible_moves);
    // check win lose tie, recursion exists
    if (available_move_size == 0){
        return 0;
    }
    if (check_win(arr) == SERVER_TYPE) {
        return 10 - depth;
    }
    if (check_win(arr) == CLIENT_TYPE) {
        return -10 + depth;
    }

    if(type == SERVER_TYPE) {
        int max = -1000;
        for(int i = 0; i < available_move_size; i++) {
            arr[possible_moves[i]] = SERVER_TYPE;
            max = get_max(max, minmax_strategy(arr, depth + 1, CLIENT_TYPE));
            //DFS backtracking
            arr[possible_moves[i]] = EMPTY_TYPE;
        }
        return max;
    } else {
        int min = 1000;
        for(int i = 0; i < available_move_size; i++) {
            arr[possible_moves[i]] = CLIENT_TYPE;
            min = get_min(min, minmax_strategy(arr, depth + 1, SERVER_TYPE));
            arr[possible_moves[i]] = EMPTY_TYPE;
        }
        return min;
    }
}

int get_max(int a, int b) {
    if(b > a) {
        return b;
    }

    return a;
}

int get_min(int a, int b) {
    if(b < a) {
        return b;
    }

    return a;
}