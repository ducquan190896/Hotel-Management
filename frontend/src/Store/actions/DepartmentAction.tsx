import AsyncStorage from "@react-native-async-storage/async-storage"
import { Dispatch } from "react";
import { HOST_URL } from "../store";
import axios from "axios";
import { ACTION } from "../../models/index.d";

export const getDepartmentsAction = () => async (dispatch: Dispatch<ACTION>, getState: any) => {
    try {
       const token : string | null = await AsyncStorage.getItem("token");  
       const res = await axios.get(HOST_URL + "/api/departments/all", {
           headers: {
               "Authorization": token ?? ""
           }
       })
       const data = await res.data
       console.log(data)
       // console.log(res.headers.authorization?? "no token")
       // await AsyncStorage.setItem("token", res.headers.authorization?? "")
       dispatch({
           type: "get_departments",
           payload: data
       })
   } catch (err) {
       dispatch({
           type: "department_RESET",
           payload: err
       })
   }  
 }

 export const ResetDepartment = () => (dispatch : Dispatch<ACTION>, getState: any) => {
    dispatch({
        type: "department_RESET"
    })
}