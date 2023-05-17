import { Text, Progress, Card, createStyles } from '@mantine/core';

const useStyles = createStyles((theme) => ({
  card: {
    backgroundColor: theme.fn.primaryColor(),
  },

  title: {
    color: theme.fn.rgba(theme.white, 0.65),
  },

  stats: {
    color: theme.white,
  },

  progressBar: {
    backgroundColor: theme.white,
  },

  progressTrack: {
    backgroundColor: theme.fn.rgba(theme.white, 0.4),
  },
}));

export function ProgressCardColored(props) {
  const { classes } = useStyles();
  return (
    <Card withBorder radius="md" p="xl" className={classes.card}>
      <Text fz="xs" tt="uppercase" fw={700} className={classes.title}>
        Cel zbiórki
      </Text>
      <Text fz="lg" fw={500} className={classes.stats}>
        {props.name}
      </Text>
      <Progress
        value={props.current/props.goal*100}
        mt="md"
        size="lg"
        radius="xl"
        classNames={{
          root: classes.progressTrack,
          bar: classes.progressBar,
        }}
      />
      <Text fz="lg" fw={500} className={classes.stats}>
        {props.current} / {props.goal}
      </Text>
    </Card>
  );
}