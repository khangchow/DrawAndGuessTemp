const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);

server.listen(3000, () => {
    console.log('listening on *:3000');
  });

  io.on('connection', (socket) => {
        socket.join('test')
        
        socket.on('NEW_LINE', (line) => {
            socket.to('test').emit('NEW_LINE', line)
        })
  })