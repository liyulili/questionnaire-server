JAR_FILENAME=$1

if test -z "${JAR_FILENAME}"
then
    error "未指定jar包"
else
    STR_ARGS=" "
    ARG_LOGGING_FILE=" --logging.file=logs/logging.log"
    LOGGING_FILE="logs/logging.log"
    while (($# >= 2))
    do
      ARG=$2
      if test ${ARG%xchars} = "--logging.file"
      then
        ${ARG_LOGGING_FILE}=${ARG}
        ${LOGGING_FILE}=${ARG#*chars}
      else
       STR_ARGS="${STR_ARGS} ${ARG}"
      fi
      shift
    done

    log "指令执行 java -jar ${JAR_FILENAME}${ARG_LOGGING_FILE}${STR_ARGS}"
    log "日志目录 ${LOGGING_FILE}"
    nohup java -jar ${JAR_FILENAME}${ARG_LOGGING_FILE}${STR_ARGS} >/dev/null &
    tail -f ${LOGGING_FILE}
fi
