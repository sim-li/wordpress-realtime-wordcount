import React, { useState } from 'react';
import SockJsClient from 'react-stomp';
import './App.css';

const SOCKET_URL = 'http://localhost:8080/ws-stats/';

const App = () => {
  const [wordCounts, setWordCounts] = useState([])

  let onConnected = () => {
    console.log("Connected!!")
  }

  let onMessageReceived = (wc) => {
    console.log('New WordCount Received!!', wc);
    setWordCounts(wordCounts.concat(wc));
  }

  return (
    <div className="App">
            <SockJsClient
              url={SOCKET_URL}
              topics={['/topic/group']}
              onConnect={onConnected}
              onDisconnect={console.log("Disconnected!")}
              onMessage={msg => onMessageReceived(msg)}
              debug={false}
            />
            <WordCounts
              wordCounts={wordCounts}
            />
           
    </div>
  )
}

const WordCounts = ({ wordCounts }) => {
  let renderWordCount = (wc) => {
      return (
          <li>
              <div className="blog-post-word-count">
                  <div className="html-content">
                      {wc.htmlContent}
                  </div>
                  <div className="word-counts">{JSON.stringify(wc.counts, null, 2) }</div>
              </div>
          </li>
      );
  };

  return (
      <ul className="wordcounts-list">
          {wordCounts.map(wc => renderWordCount(wc))}
      </ul>
  )
}

export default App;
