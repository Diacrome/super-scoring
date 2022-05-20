import { AnyAction, applyMiddleware, createStore, Store } from "redux";
import { rootReducer } from "./reducers";
import thunk, { ThunkAction, ThunkDispatch } from "redux-thunk";

export const store: Store = createStore(rootReducer, applyMiddleware(thunk));

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof store.dispatch &
  ThunkDispatch<void, undefined, AnyAction>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  AnyAction
>;
