# dno-team-02-2017
## Игра MAGIKA
## Стратегия, в которой игроки будут бить магиями друг друга. 
## Команда:
  * **Кирил Солдатов**
  * **Алёхин Владислав**
  * **Щелавин Илья**
  * **Гаджиев Магомед**

## API
###  /auth
#### Авторизация пользователя.
>* /SignIn
>     *Request:{ "email": "stark@north.io", "password": "TheWall" }

>     *Response: {
	            "response":		
  	         	{
 				"key":"200"
				"message":"success"
         	        }
	          }
                  200 - пользоваель создан
                  400 - не зарегестрирован
                  409 - введены неправильные данные
		  
#### Регистрация пользователя  
>* /signUp
>    * Request: { "email": "stark@north.io", "username": "JohnSnow", "password": "TheWall" }

>    * Responce:
        {
		"response":		
  			{
				"key":"200",
				"message":"success"
		}
	}       
         200 OK - удачная регистрация  
         400 Forbiden - уже зарегестрирован
	 409 Bad request - Введены неправильные данные
#### Получение пользователя текущей сессии  
>* /getInfoUser
>    * Request: 
            
>    * Responce:{
                  "response":		
                    {
                    "key":"200",
                    "User":"
                      {
                        "login": "JohnSnow", 
                        "password": "TheWall",
                         "email": "stark@north.io"
                    }”,
                    "message":"success"
                  }
                }  
             200 OK - удачная операция  
             400 Bad Request - не авторизовался 

#### Обновление информации о пользователе  
>* /setInfoUser
>    * Request: 
            {  
                {"login":"login1", "password":"prevpass","newpassword":"passnew"},  
            }  
	    
>    * Responce:
                  {
                        "response":
                        {
                          "message": "you don't is login",
                          "key":"400"
                        }
                   }            
                      200 OK - удачная операция  
                      400 Bad Request - не авторизовался
#### Выход пользователя  
>* /signOut
>     * Request: 

>     * Responce:
 		{
                        "response":
                        {
                          "message": "you don't is login",
                          "key":"400"
                        }
                   }   
		   200 OK - удачная операция  


