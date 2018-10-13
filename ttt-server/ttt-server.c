/**
 * test.c
 * Small Hello World! example
 * to compile with gcc, run the following command
 * gcc -o test test.c -lulfius
 */
#include <stdio.h>
#include <ulfius.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <netdb.h>
#include <jansson.h>


#define SERVER_TYPE 2
#define CLIENT_TYPE 1
#define EMPTY_TYPE 0
#define PORT 8080
int customer_id = 0;



int available_moves(int* arr, int* result) {
  int count = 0;
  for (int i = 0; i < 9; i++) {
    if(arr[i] == 0) {
      result[count++] = i;
    }
  }

  return count;
}

int check_win_helper (int* arr, int* posn) {
  if (arr[posn[0]] == arr[posn[1]] && arr[posn[1]] == arr[posn[2]]) {
    return arr[posn[0]];
  } else {
    return EMPTY_TYPE;
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

int callback_process_order(const struct _u_request * request, struct _u_response * response, void *user_data) {
  

  json_auto_t *root = ulfius_get_json_body_request(request, NULL);
  if (!root) {
    printf("root is null, exiting.\n");
  }
  srand((unsigned)time(NULL));

  json_auto_t *arr;
  arr = json_object_get(root, "oneDArray");
  int newArr[9];
  for(int i = 0; i < 9; i++){
    newArr[i] = json_integer_value(json_array_get(arr, i));
  }
  int best_move = next_best_move(newArr);


  // int pos = rand() % 9;

  // while(newArr[pos] != 0){
  //   pos = rand() % 9;
  // }
  json_auto_t *newNum = json_integer(2);
  json_array_set(arr, best_move, newNum);




  ulfius_set_json_body_response(response, 200, root);



  return U_CALLBACK_CONTINUE;
}



/**
 * main function
 */
int main(int argc, char **argv) {

  json_auto_t *userdata = json_object();
  json_auto_t *name = json_string("empty");
  json_object_set(userdata, "name", name);


  // char userdata[1024];

  struct _u_instance instance;

  // Initialize instance with the port number
  if (ulfius_init_instance(&instance, PORT, NULL, NULL) != U_OK) {
    fprintf(stderr, "Error ulfius_init_instance, abort\n");
    return(1);
  }

  // Endpoint list declaration
  
  // ulfius_add_endpoint_by_val(&instance, "GET", "/test", NULL, 0, &callback_create_order, userdata);

  // Endpoint list declaration
  
  ulfius_add_endpoint_by_val(&instance, "POST", "/test", NULL, 0, &callback_process_order, userdata);
  


  // Start the framework
  if (ulfius_start_framework(&instance) == U_OK) {
    printf("Start framework on port %d\n", instance.port);

    // Wait for the user to press <enter> on the console to quit the application
    printf("\nPlease press <ENTER> to quit.\n");
    getchar();
  } else {
    fprintf(stderr, "Error starting framework\n");
  }
  printf("End framework\n");

  ulfius_stop_framework(&instance);
  ulfius_clean_instance(&instance);

  return 0;
}