module notifications {
  requires domain;
  requires bigqueue;

  exports notifications to main;
}
