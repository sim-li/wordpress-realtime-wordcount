import React, { useState, useEffect } from "react";
import SockJsClient from "react-stomp";
import "./App.css";
import { Button } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";
import Chip from "@material-ui/core/Chip";
import ListItemText from "@material-ui/core/ListItemText";

import WhatshotIcon from "@material-ui/icons/Whatshot";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Paper from "@material-ui/core/Paper";

const SOCKET_URL = "http://localhost:8080/ws-stats/";
const REST_URL = "http://localhost:8080/wordcount";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    // maxWidth: "120ch",
    backgroundColor: theme.palette.background.paper,
    "& > *": {
      margin: theme.spacing(0.5),
    },
  },
  inline: {
    display: "inline",
  },
  bullet: {
    display: "inline-block",
    margin: "0 2px",
    transform: "scale(0.8)",
  },
  title: {
    fontSize: 14,
  },
  pos: {
    marginBottom: 12,
  },
}));

const App = () => {
  const [wordCounts, setWordCounts] = useState([]);

  let onConnected = () => {
    console.log("WebSocket Connected!");
  };

  useEffect(() => {
    fetch(REST_URL)
      .then((res) => res.json())
      .then((data) => setWordCounts(sortAndDeduplicate(data)))
      .catch(console.log);
  }, []);

  let onMessageReceived = (wc) => {
    const wcHot = {
      ...wc,
      isHot: true,
    };

    const updatedState = sortAndDeduplicate(wordCounts.concat(wcHot));

    setWordCounts(updatedState);
  };

  let sortAndDeduplicate = (wc) => {
    return wc
      .sort((a, b) => new Date(b.date) - new Date(a.date))
      .filter((item, pos, ary) => !pos || item.id !== ary[pos - 1].id);
  };

  const classes = useStyles();

  return (
    <div className="App">
      <SockJsClient
        url={SOCKET_URL}
        topics={["/topic/group"]}
        onConnect={onConnected}
        onDisconnect={console.log("Disconnected!")}
        onMessage={(msg) => onMessageReceived(msg)}
        debug={false}
      />
      <Paper elevation={0}>
        <Button color="primary">
          Welcome to word stats from a WP blog! We're addicted to live data!
        </Button>
        <List className={classes.root}>
          <WordCounts wordCounts={wordCounts} />
        </List>
      </Paper>
    </div>
  );
};

const WordCounts = ({ wordCounts }) => {
  const classes = useStyles();
  let renderWordCount = (wc) => {
    return (
      <ListItem alignItems="flex-start" key={wc.id}>
        <Card variant="outlined">
          <CardContent>
            <ListItemText
              key={wc.id}
              primary={
                <React.Fragment>
                  {!!wc.isHot ? <WhatshotIcon /> : null}
                  <div>
                    <b>ID</b>: {wc.id}
                  </div>
                </React.Fragment>
              }
              secondary={
                <React.Fragment>
                  <Divider light />
                  <br></br>

                  {Object.keys(wc.counts).map((key) => (
                    <Chip
                      key={wc.id + key}
                      variant="outlined"
                      size="small"
                      label={key + ": " + wc.counts[key]}
                    />
                  ))}
                </React.Fragment>
              }
            />
          </CardContent>
        </Card>
      </ListItem>
    );
  };

  return (
    <ul className="wordcounts-list">
      {wordCounts.map((wc) => renderWordCount(wc))}
    </ul>
  );
};

export default App;
