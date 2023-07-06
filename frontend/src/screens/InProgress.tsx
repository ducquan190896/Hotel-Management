import { StyleSheet, Text, View, ListRenderItem } from 'react-native'
import React, {useCallback, useEffect, useState, useRef, useLayoutEffect, useContext} from 'react'
import socketContext from '../Context/SocketContext';
import { useSelector } from 'react-redux';
import { HOST_URL, RootState } from '../Store/store';
import { useTailwind } from 'tailwind-rn';
import { TASK } from '../models/index.d';
import { SafeAreaView } from 'react-native-safe-area-context';
import { FlatList, TouchableOpacity } from 'react-native-gesture-handler';
import moment from 'moment';
import Entypo from 'react-native-vector-icons/Entypo';
import Feather from 'react-native-vector-icons/Feather';
import AntDesign from 'react-native-vector-icons/AntDesign';
import Ionicons from 'react-native-vector-icons/Ionicons';
import EvilIcons from 'react-native-vector-icons/EvilIcons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import SockJS from "sockjs-client";
import {over} from "stompjs"


const InProgress = () => {
  const {tasks, loadChatMessages} : any = useContext(socketContext);
  const {departments, departmentError, departmentSuccess} = useSelector((state: RootState) => state.DEPARTMENTS);
  const {authUser} = useSelector((state: RootState) => state.USERS);
  const tw = useTailwind();
  const [progressTasks, setProgressTasks] = useState<TASK[] | []>([]);
  const [stompClient, setStompClient] = useState<any | null>(null);
  
  useEffect(() => {
    if(tasks && tasks.length > 0) {
      console.log("new tasks");
      setProgressTasks(tasks.filter((t: TASK) => t.inProgress == true))
    }
  }, [tasks, loadChatMessages])

  const connect = async () => {
      loadChatMessages();
      const token = await AsyncStorage.getItem("token");
      let sock = SockJS(HOST_URL + "/socket");
      let stompClient = over(sock);
      setStompClient(stompClient);
      if(stompClient.status !== "CONNECTED") {
        stompClient.connect({Authorization: token}, (frame: any) => {
          stompClient.subscribe("/tasks/inprogress", messageReceived)
        }, notConnected)
      }
  }
  const messageReceived = (payload: any) => {
      console.log(tasks)
      const payloadTask : any = JSON.parse(payload.body);
      console.log("payload received :" )
      console.log(payloadTask)
      
      if (payloadTask.message === undefined) {
          setProgressTasks(prev => [payloadTask, ...prev])
      } else if(payloadTask.message !== undefined ) {
          if(payloadTask.message === "Complete") {
            setProgressTasks(prev => prev.filter((t : TASK) => t.id != payloadTask.id))   
          } else if(payloadTask.message === "Cancel") {
            setProgressTasks(prev => prev.filter((t : TASK) => t.id != payloadTask.id))   
          }
      }
  }

  const notConnected = () => {
      console.log("not connected");
  }

  useEffect(() => {
    if(stompClient == null) {
      connect();
    }
  }, [stompClient])



  const completeTask = async (id : number) => {
    try {
          const token = await AsyncStorage.getItem("token")
          const res = await axios.put(HOST_URL + `/api/tasks/task/${id}/complete`, {}, {
              headers: {
                  "Authorization": token ?? ""
              }
          })
          const data = await res.data
          // console.log("complete the task")
          // console.log(data)     
          
    } catch (err) {
      console.log("no tasks")
    }
  }
  const cancelTask = async (id : number) => {
    try {
          const token = await AsyncStorage.getItem("token")
          const res = await axios.put(HOST_URL + `/api/tasks/task/${id}/cancel`, {}, {
              headers: {
                  "Authorization": token ?? ""
              }
          })
          const data = await res.data
          // console.log("cancel the task")
          // console.log(data)     
              
    } catch (err) {
      console.log("no tasks")
    }
  }

  const handleRenderItem: ListRenderItem<any> = ({item}: {item: TASK}) => {
   
    return (
        <View style={[tw(`bg-white my-2 flex border  border-gray-300 ${item?.urgent == true ? "border-red-500" : "border-gray-300"} w-full relative`), {padding: 6, borderWidth: 2}]}>
            <View style={tw('flex items-center justify-center items-start')}>
              
                <View style={tw('flex flex-row items-center justify-center')}>
                  {item?.urgent && (
                    <Text style={tw('text-lg text-red-500 font-bold mr-2')}>! </Text>
                  )}
                  <Text style={tw('my-2 text-lg text-zinc-700')}>{item?.name.toUpperCase()}</Text>
                </View>
                <View style={tw('flex flex-row items-center justify-center')}>
                  <Text style={tw('text-lg text-black font-bold mr-4')}>Description: </Text>
                  <Text style={tw('text-base text-zinc-700')}>{item?.description}</Text>
                </View>
                <View style={tw('flex flex-row items-center justify-center')}>
                  <Text style={tw('text-lg text-black font-bold mr-4')}>Room: </Text>
                  <Text style={tw('text-base text-zinc-700')}>{item?.location}</Text>
                </View>
                <View style={tw('flex flex-row items-center justify-center')}>
                  <Text style={tw('text-lg text-black font-bold mr-4')}>Department: </Text>
                  <Text style={tw('text-base text-zinc-700')}>{item?.department?.name}</Text>
                </View>
                {item?.completor && (
                  <View style={tw('flex flex-row items-center justify-center')}>
                    <Text style={tw('text-lg text-black font-bold mr-4')}>Employee: </Text>
                    <Text style={tw('text-base text-zinc-700')}>{item?.completor?.firstname} {item?.completor?.surename}</Text>
                  </View>
                )}
            </View>
            <View style={[tw('absolute'), {top: 14, right: 10}]}>
              <Text style={tw('text-sm text-black')}>{moment().format(item?.dateUpdated?.toString())}</Text>
            </View>
           
            {(item?.completor?.id == authUser?.id  || authUser?.roles.includes("MANAGER")) && (
              <View style={tw('')}>
                <TouchableOpacity onPress={() => completeTask(item?.id)} style={[tw('w-2/3 h-10 bg-green-500 flex items-center justify-center mx-auto rounded-lg my-2'), {}]}>
                    <Text style={tw('text-base text-white')}>complete</Text>
                </TouchableOpacity>
                 <TouchableOpacity onPress={() => cancelTask(item?.id)} style={[tw('w-2/3 h-10 bg-red-500 flex items-center justify-center mx-auto rounded-lg my-2'), {}]}>
                    <Text style={tw('text-base text-white')}>Cancel</Text>
                </TouchableOpacity>
              </View>
            )}
        </View>
    )
}

  return (
    <SafeAreaView style={tw('flex-1 bg-gray-100 w-full h-full p-2')}>
        {progressTasks && progressTasks.length > 0 && (
            <FlatList 
                data={progressTasks}
                keyExtractor={(item: any) => item.id}
                renderItem={handleRenderItem}
                showsVerticalScrollIndicator={false}
            >
            </FlatList>
        )}
    
  </SafeAreaView>
  )
}

export default InProgress

const styles = StyleSheet.create({})