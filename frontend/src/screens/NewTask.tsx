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
import { MainStackParamList } from '../navigation/MainStack';
import { CompositeNavigationProp, useNavigation } from '@react-navigation/native';
import { DrawerStackParamList } from '../navigation/Drawer';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import SockJS from "sockjs-client";
import {over} from "stompjs"

type HomeScreenNavigationProp = CompositeNavigationProp<
NativeStackNavigationProp<DrawerStackParamList, "Home">,
NativeStackNavigationProp<MainStackParamList>
>;

const NewTask = () => {
  const navigation = useNavigation<HomeScreenNavigationProp>();
  const {tasks, loadChatMessages} : any = useContext(socketContext);
  const {departments, departmentError, departmentSuccess} = useSelector((state: RootState) => state.DEPARTMENTS);
  const {authUser} = useSelector((state: RootState) => state.USERS);
  const tw = useTailwind();
  const [newTasks, setNewTasks] = useState<TASK[] | []>([]);
  const [urgents, setUrgents] = useState<TASK[] | []>([]);
  const [stompClient, setStompClient] = useState<any | null>(null);
  
  useEffect(() => {
      if(tasks && tasks.length > 0) {
        console.log("new tasks");
        // setNewTasks(tasks.filter((t: TASK) => t.completed != true && t.cancelled != true && t.inProgress != true ))
        // setUrgents(tasks.filter((t: TASK) => t.completed != true && t.cancelled != true && t.inProgress != true && t.urgent == true ));
        setNewTasks(tasks.filter((t: TASK) => !t.completor && t.urgent == false))
        setUrgents(tasks.filter((t: TASK) => !t.completor && t.urgent == true ));
      }
  }, [ tasks,  loadChatMessages])

  const connect = async () => {
      loadChatMessages();
      const token = await AsyncStorage.getItem("token");
      let sock = SockJS(HOST_URL + "/socket");
      let stompClient = over(sock);
      setStompClient(stompClient);
      if(stompClient.status !== "CONNECTED") {
        stompClient.connect({Authorization: token}, (frame: any) => {
          stompClient.subscribe("/tasks/news", messageReceived)
        }, notConnected)
      }
  }
  const messageReceived = (payload: any) => {
      console.log(tasks)
      const payloadTask : TASK = JSON.parse(payload.body);
      console.log("payload received :" )
      console.log(payloadTask)
      if(payloadTask.urgent) {
        setUrgents(prev => [payloadTask, ...prev])
      } else {
        setNewTasks(prev => [payloadTask, ...prev])
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


  const pickTask = async (id : number) => {
    try {
          setNewTasks(prev => prev.filter((t : TASK) => t.id != id))
          setUrgents(prev => prev.filter((t : TASK) => t.id != id))
          const token = await AsyncStorage.getItem("token")
          console.log("token " + token);
          const res = await axios.put(HOST_URL + `/api/tasks/task/${id}/handleTask`, {}, {
              headers: {
                  "Authorization": token ?? ""
              }
          })
          const data = await res.data
         
          // if(stompClient != null) {
          //   stompClient.send("/app/task/pick", {}, JSON.stringify(pickTaskRequest));
          // }
          
    } catch (err) {
      console.log("failed to pick the task");
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
            </View>
            <View style={[tw('absolute'), {top: 14, right: 10}]}>
              <Text style={tw('text-sm text-black')}>{moment().format(item?.dateUpdated?.toString())}</Text>
            </View>
            <TouchableOpacity onPress={() => pickTask(item?.id)} style={[tw('w-2/3 h-10 bg-green-500 flex items-center justify-center mx-auto rounded-lg my-2'), {}]}>
              <Text style={tw('text-base text-white')}>Do it</Text>
            </TouchableOpacity>
        </View>
    
    )
}

  return (
    <SafeAreaView style={tw('flex-1 bg-gray-100 w-full h-full p-2')}>
        {urgents && urgents.length > 0 && (
            <FlatList 
                data={urgents}
                keyExtractor={(item: any) => item.id}
                renderItem={handleRenderItem}
                showsVerticalScrollIndicator={false}
            >
            </FlatList>
        )}
        {newTasks && newTasks.length > 0 && (
            <FlatList 
                data={newTasks}
                keyExtractor={(item: any) => item.id}
                renderItem={handleRenderItem}
                showsVerticalScrollIndicator={false}
            >
            </FlatList>
        )}
    
  </SafeAreaView>
  )
}

export default NewTask

const styles = StyleSheet.create({})