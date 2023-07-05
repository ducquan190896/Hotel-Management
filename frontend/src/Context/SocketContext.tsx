import { createContext, useContext, useState, useEffect } from "react"
import { TASK } from "../models/index.d";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { HOST_URL, RootState } from "../Store/store";
import { useSelector } from "react-redux";

export type socketType = {
    taskAdd: string | null,
    tasks: TASK[] | [],
    loadChatMessages: () => void
}

const defaultState : socketType = {
    taskAdd: null,
    tasks: [],
    loadChatMessages: () => {}
}

const socketContext = createContext(defaultState);

type ContextProviderProps = {
    children: React.ReactNode;
  };

export const SocketProvider = ({children}: ContextProviderProps) => {
    const [taskAdd, setTaskAdd] = useState<string | null>("test socketProvider");
    const [tasks, setTasks] = useState<TASK[] | []>([]);
    
    const loadChatMessages = async () => {
        try {
            const token = await AsyncStorage.getItem("token")
            const res = await fetch(HOST_URL + "/api/tasks/all", {
                method: "GET",
                headers: {
                    "Authorization": token ?? ""
                }
            })
            const data = await res.json()
            console.log("get_messages_of_chat")
            console.log(data)
            setTasks(data);
      } catch (err) {
         console.log("no tasks")
      }
    }


    
    return (
        <socketContext.Provider value={{taskAdd, tasks, loadChatMessages}}>
            {children}
        </socketContext.Provider>
    )
}

export default socketContext