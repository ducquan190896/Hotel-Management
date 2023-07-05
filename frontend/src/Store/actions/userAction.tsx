import AsyncStorage from "@react-native-async-storage/async-storage"
import { Dispatch } from "react";
import { HOST_URL } from "../store";
import axios from "axios";
import { ACTION, CHANGEPASSWORD, LoginForm,  UserRegisterForm } from "../../models/index.d";

export const login = (loginForm: LoginForm) => async (dispatch: Dispatch<ACTION>, getState: any) => {
    try {
        const res = await axios.put(HOST_URL + "/api/users/signIn", {
            username: loginForm.username,
            password: loginForm.password
        });
 
        const data = res.data
        console.log(res.data)
        // console.log(res.headers)
        const token : string =  res.headers.authorization ?? ""
        console.log(token)
        await AsyncStorage.setItem("token", token)
        dispatch({
            type: "LOG_IN",
            payload: data
        })
    } catch (err) {
        console.log(err);
        dispatch({
            type: "USER_ERROR",
            payload: err
        });
    }
}
 
export const Register = (registerForm: UserRegisterForm) => async (dispatch: Dispatch<ACTION>, getState: any) => {
     try {
        console.log("sign up")
        const res = await axios.post(HOST_URL + "/api/users/signup", registerForm);
        const data = await res.data
        console.log(data)
        const token : string =  res.headers.authorization?? ""
        console.log(token)
        await AsyncStorage.setItem("token", token)
        dispatch({
            type: "REGISTER",
            payload: data
        })
     } catch (err) {
      dispatch({
          type: "USER_ERROR",
          payload: err
      })
     }
  
  }
 
  export const ChangePasswordAction = (form: CHANGEPASSWORD) => async (dispatch: Dispatch<ACTION>, getState: any) => {
     try {
        const token : string | null = await AsyncStorage.getItem("token");  
        const res = await axios.put(HOST_URL + "/api/users/changePassword", form, {
            headers: {
                "Authorization": token ?? ""
            }
        })
        const data = await res.data
        // console.log(res.headers.authorization?? "no token")
        // await AsyncStorage.setItem("token", res.headers.authorization?? "")
        dispatch({
            type: "Change_Password",
            payload: data
        })
    } catch (err) {
        dispatch({
            type: "USER_ERROR",
            payload: err
        })
    }  
  }

  export const getAuthUserAction = () => async (dispatch: Dispatch<ACTION>, getState: any) => {
    try {
       const token : string | null = await AsyncStorage.getItem("token");  
       const res = await axios.get(HOST_URL + "/api/users/authUser/getAuthUser", {
           headers: {
               "Authorization": token ?? ""
           }
       })
       const data = await res.data
       // console.log(res.headers.authorization?? "no token")
       // await AsyncStorage.setItem("token", res.headers.authorization?? "")
       dispatch({
           type: "get_authUser",
           payload: data
       })
   } catch (err) {
       dispatch({
           type: "USER_ERROR",
           payload: err
       })
   }  
 }


 
  export const LogOutAction = () => async (dispatch: Dispatch<ACTION>, getState: any) => {
     try {
        const token : string | null = await AsyncStorage.getItem("token");
        await axios.get(HOST_URL + "/logout", {
            headers: {
                "Authorization": token ?? ""
            }
        })
        await AsyncStorage.setItem("token", "")



        console.log("logout");
        dispatch({
            type: "LOG_OUT"
        })
      
     } catch (err) {
        dispatch({
            type: "USER_ERROR",
            payload: err
        })
     }
  
  }

 export const ResetUser = () => (dispatch : Dispatch<ACTION>, getState: any) => {
     dispatch({
         type: "USER_RESET"
     })
 }
 