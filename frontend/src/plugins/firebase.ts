import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
const {
  VITE_FIREBASE_APIKEY,
  VITE_FIREBASE_AUTHDOMAIN,
  VITE_FIREBASE_DATABASEURL,
  VITE_FIREBASE_PROJECTID,
  VITE_FIREBASE_STORAGEBUCKET,
  VITE_FIREBASE_MESSAGING_SENDERID,
  VITE_FIREBASE_APPID,
  VITE_FIREBASE_MEASUREMENTID,
} = import.meta.env;

const firebaseConfig = {
  apiKey: VITE_FIREBASE_APIKEY,
  authDomain: VITE_FIREBASE_AUTHDOMAIN,
  databaseURL: VITE_FIREBASE_DATABASEURL,
  projectId: "jourgeois-358316",
  storageBucket: VITE_FIREBASE_STORAGEBUCKET,
  messagingSenderId: VITE_FIREBASE_MESSAGING_SENDERID,
  appId: VITE_FIREBASE_APPID,
  measurementId: VITE_FIREBASE_MEASUREMENTID,
};

const firebase = initializeApp(firebaseConfig);
export const database = getFirestore(firebase);
